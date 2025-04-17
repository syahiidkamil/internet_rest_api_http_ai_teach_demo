package com.syahiidkamil.internet_rest_http_ai_demo.service;

import com.syahiidkamil.internet_rest_http_ai_demo.model.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserByUsername(String username);
    void deleteUser(Long id);
}
