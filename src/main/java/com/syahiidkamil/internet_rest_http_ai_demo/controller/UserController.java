package com.syahiidkamil.internet_rest_http_ai_demo.controller;

import com.syahiidkamil.internet_rest_http_ai_demo.model.dto.response.WebResponse;
import com.syahiidkamil.internet_rest_http_ai_demo.model.entity.User;
import com.syahiidkamil.internet_rest_http_ai_demo.service.UserService;
import com.syahiidkamil.internet_rest_http_ai_demo.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final ApiResponse apiResponse;

    public UserController(UserService userService, ApiResponse apiResponse) {
        this.userService = userService;
        this.apiResponse = apiResponse;
    }

    @GetMapping
    public ResponseEntity<WebResponse<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(
                apiResponse.success(users)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebResponse<User>> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(
                apiResponse.success(user)
        );
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<WebResponse<User>> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(
                apiResponse.success(user)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WebResponse<String>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(
                apiResponse.success("User deleted successfully")
        );
    }
}
