package com.syahiidkamil.internet_rest_http_ai_demo.model.dto.response;

public class LoginResponse {
    private String token;
    private String username;
    private String name;
    private String role;

    public LoginResponse() {
    }

    public LoginResponse(String token, String username, String name, String role) {
        this.token = token;
        this.username = username;
        this.name = name;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static LoginResponseBuilder builder() {
        return new LoginResponseBuilder();
    }

    public static class LoginResponseBuilder {
        private String token;
        private String username;
        private String name;
        private String role;

        LoginResponseBuilder() {
        }

        public LoginResponseBuilder token(String token) {
            this.token = token;
            return this;
        }

        public LoginResponseBuilder username(String username) {
            this.username = username;
            return this;
        }

        public LoginResponseBuilder name(String name) {
            this.name = name;
            return this;
        }

        public LoginResponseBuilder role(String role) {
            this.role = role;
            return this;
        }

        public LoginResponse build() {
            return new LoginResponse(token, username, name, role);
        }
    }
}
