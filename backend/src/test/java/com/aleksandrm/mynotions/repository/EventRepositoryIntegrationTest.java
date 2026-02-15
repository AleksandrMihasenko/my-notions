package com.aleksandrm.mynotions.repository;

import com.aleksandrm.mynotions.support.IntegrationTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EventRepositoryIntegrationTest extends IntegrationTestBase {

    @Autowired
    private EventRepository eventRepository;
    private Long userId;

    @BeforeEach
    void setUp() {
        userId = jdbcTemplate.queryForObject(
                "INSERT INTO users (email, password_hash, full_name) VALUES (?, ?, ?) RETURNING id",
                Long.class,
                "test@example.com",
                "hashed_password",
                "Test User"
        );
    }

    @Test
    @DisplayName("Register/Login: logEvent saves event to database")
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
