package com.aleksandrm.mynotions.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import com.aleksandrm.mynotions.support.PostgresIntegrationTestBase;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WorkspaceControllerIntegrationTest extends PostgresIntegrationTestBase {

    @Autowired
    private TestRestTemplate restTemplate;

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

    @Test
    @DisplayName("Get workspace by id: owner requests existing workspace -> returns 200")
    void getWorkspaceByIdOwnerRequestsExistingWorkspaceReturnsOk() {
        String token = registerAndGetToken("workspace_get@email.com", "password");
        ResponseEntity<Map<String, Object>> createResponse = createWorkspace(token, "Workspace for get");
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());

        Long workspaceId = ((Number) createResponse.getBody().get("id")).longValue();

        ResponseEntity<Map<String, Object>> response = getWorkspaceById(token, workspaceId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(workspaceId, ((Number) response.getBody().get("id")).longValue());
        assertEquals("Workspace for get", response.getBody().get("name"));
    }

    @Test
    @DisplayName("Update workspace: owner sends valid data -> returns 200 and updates DB")
    void updateWorkspaceOwnerSendsValidDataReturnsOk() {
        String token = registerAndGetToken("workspace_update@email.com", "password");
        ResponseEntity<Map<String, Object>> createResponse = createWorkspace(token, "Old workspace");
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());

        Long workspaceId = ((Number) createResponse.getBody().get("id")).longValue();
        ResponseEntity<Map<String, Object>> updateResponse = updateWorkspace(token, workspaceId, "Updated workspace");

        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertNotNull(updateResponse.getBody());
        assertEquals("Updated workspace", updateResponse.getBody().get("name"));

        String workspaceName = jdbcTemplate.queryForObject(
                "SELECT name FROM workspaces WHERE id = ?",
                String.class,
                workspaceId
        );
        assertEquals("Updated workspace", workspaceName);
    }

    @Test
    @DisplayName("Delete workspace: owner deletes existing workspace -> returns 204 and workspace not found later")
    void deleteWorkspaceOwnerDeletesExistingWorkspaceReturnsNoContent() {
        String token = registerAndGetToken("workspace_delete@email.com", "password");
        ResponseEntity<Map<String, Object>> createResponse = createWorkspace(token, "Workspace to delete");
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());

        Long workspaceId = ((Number) createResponse.getBody().get("id")).longValue();

        ResponseEntity<Void> deleteResponse = deleteWorkspace(token, workspaceId);
        ResponseEntity<String> getAfterDeleteResponse = getWorkspaceByIdAsString(token, workspaceId);

        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, getAfterDeleteResponse.getStatusCode());
        assertNotNull(getAfterDeleteResponse.getBody());
        assertTrue(getAfterDeleteResponse.getBody().contains("Workspace not found"));
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

    private ResponseEntity<Map<String, Object>> getWorkspaceById(String token, Long workspaceId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                "/api/workspaces/" + workspaceId,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );
    }

    private ResponseEntity<String> getWorkspaceByIdAsString(String token, Long workspaceId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                "/api/workspaces/" + workspaceId,
                HttpMethod.GET,
                entity,
                String.class
        );
    }

    private ResponseEntity<Map<String, Object>> updateWorkspace(String token, Long workspaceId, String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(Map.of("name", name), headers);

        return restTemplate.exchange(
                "/api/workspaces/" + workspaceId,
                HttpMethod.PUT,
                entity,
                new ParameterizedTypeReference<>() {}
        );
    }

    private ResponseEntity<Void> deleteWorkspace(String token, Long workspaceId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                "/api/workspaces/" + workspaceId,
                HttpMethod.DELETE,
                entity,
                Void.class
        );
    }
}
