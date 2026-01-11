package com.aleksandrm.mynotions.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class AuthControllerIntegrationTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:16-alpine");

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("DELETE FROM events");
        jdbcTemplate.update("DELETE FROM users");
    }

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
}
