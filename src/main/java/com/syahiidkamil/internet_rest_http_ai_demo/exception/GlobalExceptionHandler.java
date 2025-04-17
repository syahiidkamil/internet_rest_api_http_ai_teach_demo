package com.syahiidkamil.internet_rest_http_ai_demo.exception;

import com.syahiidkamil.internet_rest_http_ai_demo.model.dto.response.WebResponse;
import com.syahiidkamil.internet_rest_http_ai_demo.util.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ApiResponse apiResponse;

    public GlobalExceptionHandler(ApiResponse apiResponse) {
        this.apiResponse = apiResponse;
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<WebResponse<Object>> handleCustomException(CustomException ex) {
        WebResponse<Object> response = apiResponse.error(ex.getHttpStatus(), ex.getMessage());
        return new ResponseEntity<>(response, ex.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<WebResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder("Validation error: ");
        ex.getBindingResult().getAllErrors().forEach(error -> {
            errorMessage.append(error.getDefaultMessage()).append("; ");
        });
        WebResponse<Object> response = apiResponse.error(HttpStatus.BAD_REQUEST, errorMessage.toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<WebResponse<Object>> handleConstraintViolation(ConstraintViolationException ex) {
        StringBuilder errorMessage = new StringBuilder("Validation error: ");
        ex.getConstraintViolations().forEach(violation -> {
            errorMessage.append(violation.getMessage()).append("; ");
        });
        WebResponse<Object> response = apiResponse.error(HttpStatus.BAD_REQUEST, errorMessage.toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<WebResponse<Object>> handleWebClientResponseException(WebClientResponseException ex) {
        WebResponse<Object> response = apiResponse.error(
                HttpStatus.valueOf(ex.getStatusCode().value()), 
                "External API error: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getStatusCode().value()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<WebResponse<Object>> handleGlobalException(Exception ex) {
        WebResponse<Object> response = apiResponse.error(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "An unexpected error occurred: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
