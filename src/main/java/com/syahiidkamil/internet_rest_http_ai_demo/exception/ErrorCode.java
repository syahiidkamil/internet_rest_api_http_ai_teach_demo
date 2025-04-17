package com.syahiidkamil.internet_rest_http_ai_demo.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // Auth related errors
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Invalid username or password"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized access"),
    
    // User related errors
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    USERNAME_EXISTS(HttpStatus.BAD_REQUEST, "Username already exists"),
    EMAIL_EXISTS(HttpStatus.BAD_REQUEST, "Email already exists"),
    
    // Product related errors
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "Product not found"),
    INVALID_PRODUCT_DATA(HttpStatus.BAD_REQUEST, "Invalid product data"),
    
    // AI related errors
    AI_SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "AI service is currently unavailable"),
    AI_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "Error in AI request"),
    
    // Generic errors
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad request"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
