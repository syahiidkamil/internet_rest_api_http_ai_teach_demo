package com.syahiidkamil.internet_rest_http_ai_demo.service;

import com.syahiidkamil.internet_rest_http_ai_demo.model.dto.request.LoginRequest;
import com.syahiidkamil.internet_rest_http_ai_demo.model.dto.request.RegisterRequest;
import com.syahiidkamil.internet_rest_http_ai_demo.model.dto.response.LoginResponse;
import com.syahiidkamil.internet_rest_http_ai_demo.model.entity.User;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    User register(RegisterRequest request);
    User validateToken(String token);
}
