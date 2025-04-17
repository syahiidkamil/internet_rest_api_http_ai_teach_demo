package com.syahiidkamil.internet_rest_http_ai_demo.service.impl;

import com.syahiidkamil.internet_rest_http_ai_demo.exception.CustomException;
import com.syahiidkamil.internet_rest_http_ai_demo.exception.ErrorCode;
import com.syahiidkamil.internet_rest_http_ai_demo.model.entity.User;
import com.syahiidkamil.internet_rest_http_ai_demo.repository.UserRepository;
import com.syahiidkamil.internet_rest_http_ai_demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
