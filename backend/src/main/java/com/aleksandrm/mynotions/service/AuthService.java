package com.aleksandrm.mynotions.service;

import com.aleksandrm.mynotions.dto.AuthResponse;
import com.aleksandrm.mynotions.dto.LoginRequest;
import com.aleksandrm.mynotions.dto.RegisterRequest;
import com.aleksandrm.mynotions.model.User;
import com.aleksandrm.mynotions.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordService passwordService;

    public AuthService(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    public AuthResponse register(RegisterRequest request) {
        Optional<User> existingUser = userRepository.getUserByEmail(request.getEmail());

        if (existingUser.isPresent()) {
            throw  new RuntimeException("Email already taken");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordService.hash(request.getPassword()));
        user.setFullName("Anonymous");
        user.setRole("USER");
        user.setActive(true);

        User savedUser = userRepository.save(user);

        return toAuthResponse(savedUser);
    }

    public AuthResponse login(LoginRequest request) {
        Optional<User> user = userRepository.getUserByEmail(request.getEmail());
        if (user.isEmpty()) {
            throw  new RuntimeException("Invalid credentials");
        }

        User savedUser = user.get();

        boolean isPasswordCorrect = passwordService.match(request.getPassword(), user.get().getPasswordHash());
        if (!isPasswordCorrect) {
            throw  new RuntimeException("Invalid credentials");
        }

        return toAuthResponse(savedUser);
    }

    private AuthResponse toAuthResponse(User user) {
        AuthResponse authResponse = new AuthResponse();
        authResponse.setId(user.getId());
        authResponse.setEmail(user.getEmail());
        authResponse.setFullName(user.getFullName());
        authResponse.setRole(user.getRole());
        authResponse.setActive(user.isActive());
        authResponse.setCreatedAt(user.getCreatedAt());
        authResponse.setUpdatedAt(user.getUpdatedAt());
        return authResponse;
    }
}
