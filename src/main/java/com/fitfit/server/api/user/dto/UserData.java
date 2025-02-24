package com.fitfit.server.api.user.dto;

public record UserData(
        String name,
        String email,
        String profileImageUrl
) { }