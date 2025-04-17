package com.syahiidkamil.internet_rest_http_ai_demo.util;

import com.syahiidkamil.internet_rest_http_ai_demo.model.dto.response.WebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ApiResponse {

    public <T> WebResponse<T> success(T data) {
        return WebResponse.<T>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.name())
                .message("Operation successful")
                .data(data)
                .build();
    }

    public <T> WebResponse<T> success(T data, String message) {
        return WebResponse.<T>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.name())
                .message(message)
                .data(data)
                .build();
    }

    public <T> WebResponse<T> created(T data) {
        return WebResponse.<T>builder()
                .code(HttpStatus.CREATED.value())
                .status(HttpStatus.CREATED.name())
                .message("Resource created successfully")
                .data(data)
                .build();
    }

    public <T> WebResponse<T> error(HttpStatus status, String message) {
        return WebResponse.<T>builder()
                .code(status.value())
                .status(status.name())
                .message(message)
                .data(null)
                .build();
    }
}
