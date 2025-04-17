package com.syahiidkamil.internet_rest_http_ai_demo.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    
    private final HttpStatus httpStatus;
    private final String message;

    public CustomException(ErrorCode errorCode) {
        this.httpStatus = errorCode.getHttpStatus();
        this.message = errorCode.getMessage();
    }

    public CustomException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
