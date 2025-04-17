package com.syahiidkamil.internet_rest_http_ai_demo.config;

import com.syahiidkamil.internet_rest_http_ai_demo.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Simple security config for demonstration purposes
 * In a real application, you would use Spring Security
 */
@Configuration
public class SecurityConfig {

    private final AuthService authService;

    // List of secured endpoints that require authentication
    private static final List<String> SECURED_PATHS = Arrays.asList(
            "/api/ai/generate-secure"
            // Add more secured paths as needed
    );

    public SecurityConfig(AuthService authService) {
        this.authService = authService;
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(authService);
    }

    @Component
    @Order(1)
    public class JwtAuthFilter extends OncePerRequestFilter {

        private final AuthService authService;

        public JwtAuthFilter(AuthService authService) {
            this.authService = authService;
        }

        @Override
        protected void doFilterInternal(
                HttpServletRequest request,
                HttpServletResponse response,
                FilterChain filterChain) throws ServletException, IOException {

            String path = request.getRequestURI();
            
            // Skip non-secured paths
            boolean isSecuredPath = SECURED_PATHS.stream()
                    .anyMatch(path::startsWith);
            
            if (!isSecuredPath) {
                filterChain.doFilter(request, response);
                return;
            }

            // Check for Authorization header
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Unauthorized: Missing or invalid Authorization header");
                return;
            }

            try {
                // Extract and validate token
                String token = authHeader.substring(7);
                authService.validateToken(token);
                
                // Token is valid, proceed
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Unauthorized: " + e.getMessage());
            }
        }
    }
}
