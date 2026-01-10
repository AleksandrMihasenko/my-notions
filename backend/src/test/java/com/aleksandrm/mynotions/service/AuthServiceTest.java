package com.aleksandrm.mynotions.service;

import com.aleksandrm.mynotions.dto.LoginRequest;
import com.aleksandrm.mynotions.dto.RegisterRequest;
import com.aleksandrm.mynotions.model.User;
import com.aleksandrm.mynotions.repository.EventRepository;
import com.aleksandrm.mynotions.repository.UserRepository;
import com.aleksandrm.mynotions.utils.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock private UserRepository userRepository;
    @Mock private PasswordService passwordService;
    @Mock private JwtUtil jwtUtil;
    @Mock private EventRepository eventRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("Register: new email -> returns auth response")
    void registerNewEmailReturnsAuthResponse() {
        //Arrange
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail("test@example.com");

        when(userRepository.getUserByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passwordService.hash("password")).thenReturn("hashed");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(jwtUtil.generateToken(savedUser)).thenReturn("token");

        // Act
        var response = authService.register(request);

        // Assert
        assertNotNull(response);
        assertEquals("token", response.getToken());
        assertEquals(1L, response.getId());
        verify(eventRepository).logEvent("USER_REGISTERED", 1L, "{}");
    }

    @Test
    @DisplayName("Register: existing email -> throws exception")
    void registerExistingEmailThrowsError() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setEmail("test@example.com");

        when(userRepository.getUserByEmail("test@example.com")).thenReturn(Optional.of(existingUser));

        // Act
        assertThrows(RuntimeException.class, () -> authService.register(request));

        // Assert
        verify(userRepository, never()).save(any(User.class));
        verify(eventRepository, never()).logEvent(anyString(), anyLong(), anyString());
    }

    @Test
    @DisplayName("Login: valid credentials -> returns auth response")
    void loginValidCredentialsReturnsAuthResponse() {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail("test@example.com");
        savedUser.setPasswordHash("hashed");

        when(userRepository.getUserByEmail("test@example.com")).thenReturn(Optional.of(savedUser));
        when(passwordService.match(request.getPassword(), savedUser.getPasswordHash())).thenReturn(true);
        when(jwtUtil.generateToken(savedUser)).thenReturn("token");

        // Act
        var response = authService.login(request);

        // Assert
        assertNotNull(response);
        assertEquals("token", response.getToken());
        assertEquals(1L, response.getId());

        verify(eventRepository).logEvent("USER_LOGGED_IN", 1L, "{}");
    }

    @Test
    @DisplayName("Login: email not found -> throws exception")
    void loginInvalidEmailThrowsError() {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        when(userRepository.getUserByEmail("test@example.com")).thenReturn(Optional.empty());

        // Act
        assertThrows(RuntimeException.class, () -> authService.login(request));

        // Assert
        verify(eventRepository, never()).logEvent(anyString(), anyLong(), anyString());
    }

    @Test
    @DisplayName("Login: invalid credentials -> throws exception")
    void loginInvalidCredentialsThrowsError() {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail("test@example.com");
        savedUser.setPasswordHash("hashed");

        when(userRepository.getUserByEmail("test@example.com")).thenReturn(Optional.of(savedUser));
        when(passwordService.match(request.getPassword(), savedUser.getPasswordHash())).thenReturn(false);

        // Act
        assertThrows(RuntimeException.class, () -> authService.login(request));

        // Assert
        verify(eventRepository, never()).logEvent(anyString(), anyLong(), anyString());
    }
}
