package com.aleksandrm.mynotions.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.aleksandrm.mynotions.support.PostgresIntegrationTestBase;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerIntegrationTest extends PostgresIntegrationTestBase {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Register: user sends valid data -> user registered")
    void userSendsValidDataAndRegistered() {
        // Arrange
        Map<String, String> request = Map.of(
                "email", "example@test.com",
                "password", "testpassword"
        );

        // Act
        ResponseEntity<Map> response = restTemplate.postForEntity(
                "/api/auth/register",
                request,
                Map.class
        );

        // Assert HTTP response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().get("token"));

        // Assert database
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM users WHERE email = ?",
                Integer.class,
                "example@test.com"
        );
        assertEquals(1, count);
    }

    @Test
    @DisplayName("Register: user sends invalid email -> user gets 400 Bad Request")
    void userSendsInvalidDataAndNotRegistered() {
        // Arrange
        Map<String, String> request = Map.of(
                "email", "invalid",
                "password", "testpassword"
        );

        // Act
        ResponseEntity<Map> response = restTemplate.postForEntity(
                "/api/auth/register",
                request,
                Map.class
        );

        // Assert HTTP response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().contains("email"));

        // Assert
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM users WHERE email = ?",
                Integer.class,
                "invalid"
        );
        assertEquals(0, count);
    }

    @Test
    @DisplayName("Register: user sends existing email -> user gets 409 Conflict")
    void userSendsExistingEmailAndGetsConflict() {
        // Arrange
        Map<String, String> request = Map.of(
                "email", "duplicate@test.com",
                "password", "testpassword"
        );

        ResponseEntity<Map> firstResponse = restTemplate.postForEntity(
                "/api/auth/register",
                request,
                Map.class
        );
        assertEquals(HttpStatus.CREATED, firstResponse.getStatusCode());

        // Act
        ResponseEntity<String> secondResponse = restTemplate.postForEntity(
                "/api/auth/register",
                request,
                String.class
        );

        // Assert
        assertEquals(HttpStatus.CONFLICT, secondResponse.getStatusCode());
        assertNotNull(secondResponse.getBody());
        assertTrue(secondResponse.getBody().contains("Email already"));
    }

    @Test
    @DisplayName("Login: user sends invalid credentials -> user gets 401 Unauthorized")
    void userSendsInvalidCredentialsAndGetsUnauthorized() {
        // Arrange
        Map<String, String> registerRequest = Map.of(
                "email", "login@test.com",
                "password", "correctpassword"
        );
        ResponseEntity<Map> registerResponse = restTemplate.postForEntity(
                "/api/auth/register",
                registerRequest,
                Map.class
        );
        assertEquals(HttpStatus.CREATED, registerResponse.getStatusCode());

        Map<String, String> loginRequest = Map.of(
                "email", "login@test.com",
                "password", "wrongpassword"
        );

        // Act
        ResponseEntity<String> loginResponse = restTemplate.postForEntity(
                "/api/auth/login",
                loginRequest,
                String.class
        );

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, loginResponse.getStatusCode());
        assertNotNull(loginResponse.getBody());
        assertTrue(loginResponse.getBody().contains("Invalid credentials"));
    }
}
