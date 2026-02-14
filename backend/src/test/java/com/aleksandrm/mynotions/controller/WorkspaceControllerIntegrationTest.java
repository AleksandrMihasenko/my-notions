package com.aleksandrm.mynotions.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class WorkspaceControllerIntegrationTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:16-alpine");

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("DELETE FROM pages");
        jdbcTemplate.update("DELETE FROM workspaces");
        jdbcTemplate.update("DELETE FROM events");
        jdbcTemplate.update("DELETE FROM users");
    }

    @Test
    @DisplayName("Create workspace: valid request (token + body) -> returns 201 and row in DB")
    void createWorkspaceValidRequestReturnsCreated() {
        // Arrange
        String token = registerAndGetToken("test@email.com", "password");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> request = Map.of("name", "Test Workspace");
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

        // Act
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                "/api/workspaces",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Workspace", response.getBody().get("name"));
        assertNotNull(response.getBody().get("id"));

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM workspaces WHERE name = ?",
                Integer.class,
                "Test Workspace"
        );
        assertEquals(1, count);
    }

    @Test
    @DisplayName("Create workspace: unauthorized request -> returns 403")
    void createWorkspaceUnauthorizedRequestReturnsError() {
        // Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> request = Map.of("name", "Test Workspace");
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

        // Act
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                "/api/workspaces",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNull(response.getBody());

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM workspaces WHERE name = ?",
                Integer.class,
                "Test Workspace"
        );
        assertEquals(0, count);
    }

    @Test
    @DisplayName("Create workspace: invalid name -> returns 400")
    void createWorkspaceWithInvalidNameReturnsError() {
        // Arrange
        String token = registerAndGetToken("test@email.com", "password");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> request = Map.of("name", "");
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

        // Act
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                "/api/workspaces",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().contains("name"));

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM workspaces WHERE name = ?",
                Integer.class,
                "Test Workspace"
        );
        assertEquals(0, count);
    }

    @Test
    @DisplayName("Get workspace: user -> returns only current user workspaces")
    void getWorkspaceReturnsOnlyCurrentUserWorkspaces() {
        // Arrange
        String user1 = registerAndGetToken("test1@email.com", "password1");
        String user2 = registerAndGetToken("test2@email.com", "password2");

        ResponseEntity<Map<String, Object>> response1 = createWorkspace(user1, "Workspace 1");
        ResponseEntity<Map<String, Object>> response2 = createWorkspace(user1, "Workspace 2");
        ResponseEntity<Map<String, Object>> response3 = createWorkspace(user2, "Workspace X");

        assertEquals(HttpStatus.CREATED, response1.getStatusCode());
        assertEquals(HttpStatus.CREATED, response2.getStatusCode());
        assertEquals(HttpStatus.CREATED, response3.getStatusCode());

        HttpHeaders headersUser1 = new HttpHeaders();
        headersUser1.setBearerAuth(user1);
        HttpEntity<Void> entity = new HttpEntity<>(headersUser1);

        // Act
        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                "/api/workspaces",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        Set<String> names = response.getBody().stream()
                .map(item -> (String) item.get("name"))
                .collect(Collectors.toSet());

        assertTrue(names.contains("Workspace 1"));
        assertTrue(names.contains("Workspace 2"));
        assertFalse(names.contains("Workspace X"));
    }

    private String registerAndGetToken(String email, String password) {
        Map<String, String> request = Map.of("email", email, "password", password);
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                "/api/auth/register",
                HttpMethod.POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<>() {}
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        return (String) response.getBody().get("token");
    }

    private ResponseEntity<Map<String, Object>> createWorkspace(String token, String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> request = Map.of("name", name);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

        return restTemplate.exchange(
                "/api/workspaces",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {}
        );
    }
}
