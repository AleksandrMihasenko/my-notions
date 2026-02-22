package com.aleksandrm.mynotions.service;

import com.aleksandrm.mynotions.dto.AuthResponse;
import com.aleksandrm.mynotions.dto.LoginRequest;
import com.aleksandrm.mynotions.dto.RegisterRequest;
import com.aleksandrm.mynotions.events.UserLoggedEvent;
import com.aleksandrm.mynotions.events.UserRegisteredEvent;
import com.aleksandrm.mynotions.exception.EmailAlreadyTakenException;
import com.aleksandrm.mynotions.exception.InvalidCredentialsException;
import com.aleksandrm.mynotions.model.User;
import com.aleksandrm.mynotions.repository.UserRepository;
import com.aleksandrm.mynotions.utils.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final JwtUtil jwtUtil;
    private final ApplicationEventPublisher publisher;

    public AuthService(UserRepository userRepository, PasswordService passwordService, JwtUtil jwtUtil, ApplicationEventPublisher publisher) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
        this.jwtUtil = jwtUtil;
        this.publisher = publisher;
    }

    public AuthResponse register(RegisterRequest request) {
        Optional<User> existingUser = userRepository.getUserByEmail(request.getEmail());

        if (existingUser.isPresent()) {
            throw new EmailAlreadyTakenException("Email already taken");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordService.hash(request.getPassword()));
        user.setFullName("Anonymous");
        user.setRole("USER");
        user.setActive(true);

        User savedUser = userRepository.save(user);
        String token = jwtUtil.generateToken(savedUser);

        publisher.publishEvent(new UserRegisteredEvent(savedUser.getId(), "{}"));

        return toAuthResponse(token, savedUser);
    }

    public AuthResponse login(LoginRequest request) {
        Optional<User> user = userRepository.getUserByEmail(request.getEmail());
        if (user.isEmpty()) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        User savedUser = user.get();

        boolean isPasswordCorrect = passwordService.match(request.getPassword(), user.get().getPasswordHash());
        if (!isPasswordCorrect) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(savedUser);

        publisher.publishEvent(new UserLoggedEvent(savedUser.getId(), "{}"));

        return toAuthResponse(token, savedUser);
    }

    private AuthResponse toAuthResponse(String token, User user) {
        AuthResponse authResponse = new AuthResponse();
        authResponse.setId(user.getId());
        authResponse.setEmail(user.getEmail());
        authResponse.setFullName(user.getFullName());
        authResponse.setRole(user.getRole());
        authResponse.setActive(user.isActive());
        authResponse.setCreatedAt(user.getCreatedAt());
        authResponse.setUpdatedAt(user.getUpdatedAt());
        authResponse.setToken(token);
        return authResponse;
    }
}
