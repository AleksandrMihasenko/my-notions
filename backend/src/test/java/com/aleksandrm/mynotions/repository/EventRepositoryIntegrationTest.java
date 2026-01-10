package com.aleksandrm.mynotions.repository;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.jdbc.core.JdbcTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
public class EventRepositoryIntegrationTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:16-alpine");

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Long userId;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("DELETE FROM events");
        jdbcTemplate.update("DELETE FROM users");

        userId = jdbcTemplate.queryForObject(
                "INSERT INTO users (email, password_hash, full_name) VALUES (?, ?, ?) RETURNING id",
                Long.class,
                "test@example.com",
                "hashed_password",
                "Test User"
        );
    }

    @Test
    @DisplayName("logEvent saves event to database")
    void logEventSavesEventToDatabase() {
        // Arrange
        String eventType = "TEST_EVENT";
        String metadata = "{\"test\": true}";

        // Act
        eventRepository.logEvent(eventType, userId, metadata);

        // Assert
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM events WHERE user_id = ? AND event_type = ?",
                Integer.class,
                userId, eventType
        );

        assertEquals(1, count);
    }
}