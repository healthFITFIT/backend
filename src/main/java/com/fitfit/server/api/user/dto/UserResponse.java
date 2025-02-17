package com.fitfit.server.api.user.dto;

public class UserResponse {
    private Long userId;
    private String email;
    private String name;
    private String role;

    // Constructor
    public UserResponse(Long userId, String email, String name, String role) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.role = role;
    }
}