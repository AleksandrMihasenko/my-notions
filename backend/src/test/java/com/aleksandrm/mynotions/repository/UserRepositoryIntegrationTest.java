package com.aleksandrm.mynotions.repository;

import com.aleksandrm.mynotions.model.User;
import com.aleksandrm.mynotions.support.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryIntegrationTest extends IntegrationTestBase {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Register: save user -> return generated fields")
    void saveUserAndReturnGeneratedFields() {
        //Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setPasswordHash("hashed");
        user.setFullName("Test User");
        user.setRole("USER");
        user.setActive(true);

        //Act
        User savedUser = userRepository.save(user);

        //Assert
        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getFullName(), savedUser.getFullName());
        assertEquals(user.getRole(), savedUser.getRole());
        assertEquals(user.isActive(), savedUser.isActive());
        assertNotNull(savedUser.getCreatedAt());
        assertNotNull(savedUser.getUpdatedAt());

    }

    @Test
    @DisplayName("Login: email -> returns user when email exists")
    void getUserByEmailReturnsUserWhenEmailExists() {
        // Arrange
        Long id = jdbcTemplate.queryForObject(
                "INSERT INTO users (email, password_hash, full_name) VALUES (?, ?, ?) RETURNING id",
                Long.class,
                "exists@example.com",
                "hashed",
                "Exists User"
        );

        // Act
        Optional<User> found = userRepository.getUserByEmail("exists@example.com");

        assertTrue(found.isPresent());
        assertEquals(id, found.get().getId());
        assertEquals("exists@example.com", found.get().getEmail());
    }

    @Test
    @DisplayName("Login: wrong email -> returns empty")
    void getUserByEmailReturnsEmptyWhenEmailNotExists() {
        // Arrange
        // Act
        Optional<User> found = userRepository.getUserByEmail("missing@example.com");

        assertTrue(found.isEmpty());
    }

}
