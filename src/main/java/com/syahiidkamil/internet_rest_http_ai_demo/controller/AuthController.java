package com.syahiidkamil.internet_rest_http_ai_demo.controller;

import com.syahiidkamil.internet_rest_http_ai_demo.model.dto.request.LoginRequest;
import com.syahiidkamil.internet_rest_http_ai_demo.model.dto.request.RegisterRequest;
import com.syahiidkamil.internet_rest_http_ai_demo.model.dto.response.LoginResponse;
import com.syahiidkamil.internet_rest_http_ai_demo.model.dto.response.WebResponse;
import com.syahiidkamil.internet_rest_http_ai_demo.model.entity.User;
import com.syahiidkamil.internet_rest_http_ai_demo.service.AuthService;
import com.syahiidkamil.internet_rest_http_ai_demo.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final ApiResponse apiResponse;

    public AuthController(AuthService authService, ApiResponse apiResponse) {
        this.authService = authService;
        this.apiResponse = apiResponse;
    }

    @PostMapping("/login")
    public ResponseEntity<WebResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.login(request);
        return ResponseEntity.ok(
                apiResponse.success(loginResponse, "Login successful")
        );
    }

    @PostMapping("/register")
    public ResponseEntity<WebResponse<User>> register(@RequestBody RegisterRequest request) {
        User user = authService.register(request);
        return new ResponseEntity<>(
                apiResponse.created(user),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/validate")
    public ResponseEntity<WebResponse<User>> validateToken(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7); // Remove "Bearer " prefix
        User user = authService.validateToken(token);
        return ResponseEntity.ok(
                apiResponse.success(user, "Token is valid")
        );
    }
}
