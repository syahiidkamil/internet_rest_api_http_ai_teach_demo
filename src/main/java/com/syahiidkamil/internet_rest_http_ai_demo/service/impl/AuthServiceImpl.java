package com.syahiidkamil.internet_rest_http_ai_demo.service.impl;

import com.syahiidkamil.internet_rest_http_ai_demo.exception.CustomException;
import com.syahiidkamil.internet_rest_http_ai_demo.exception.ErrorCode;
import com.syahiidkamil.internet_rest_http_ai_demo.model.dto.request.LoginRequest;
import com.syahiidkamil.internet_rest_http_ai_demo.model.dto.request.RegisterRequest;
import com.syahiidkamil.internet_rest_http_ai_demo.model.dto.response.LoginResponse;
import com.syahiidkamil.internet_rest_http_ai_demo.model.entity.User;
import com.syahiidkamil.internet_rest_http_ai_demo.repository.UserRepository;
import com.syahiidkamil.internet_rest_http_ai_demo.service.AuthService;
import com.syahiidkamil.internet_rest_http_ai_demo.util.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_CREDENTIALS));

        // In a real app, we'd use proper password hashing and comparison
        if (!Objects.equals(user.getPassword(), request.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return LoginResponse.builder()
                .token(token)
                .username(user.getUsername())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }

    @Override
    public User register(RegisterRequest request) {
        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CustomException(ErrorCode.USERNAME_EXISTS);
        }

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(ErrorCode.EMAIL_EXISTS);
        }

        // In a real app, we'd hash the password
        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .name(request.getName())
                .email(request.getEmail())
                .role("USER") // Default role
                .build();

        return userRepository.save(user);
    }

    @Override
    public User validateToken(String token) {
        try {
            String username = jwtUtil.extractUsername(token);
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        } catch (Exception e) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }
    }
}
