package com.syahiidkamil.internet_rest_http_ai_demo.model.dto.request;

public class RegisterRequest {
    private String username;
    private String password;
    private String name;
    private String email;

    public RegisterRequest() {
    }

    public RegisterRequest(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static RegisterRequestBuilder builder() {
        return new RegisterRequestBuilder();
    }

    public static class RegisterRequestBuilder {
        private String username;
        private String password;
        private String name;
        private String email;

        RegisterRequestBuilder() {
        }

        public RegisterRequestBuilder username(String username) {
            this.username = username;
            return this;
        }

        public RegisterRequestBuilder password(String password) {
            this.password = password;
            return this;
        }

        public RegisterRequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        public RegisterRequestBuilder email(String email) {
            this.email = email;
            return this;
        }

        public RegisterRequest build() {
            return new RegisterRequest(username, password, name, email);
        }
    }
}
