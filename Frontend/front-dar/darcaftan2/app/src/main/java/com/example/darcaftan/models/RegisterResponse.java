package com.example.darcaftan.models;

public class RegisterResponse {
    private String message;
    private User user;
    private String token;

    // Getters
    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}
