package com.fitfit.server.api.user.dto;

public record UserUpdateRequest(
        String name,
        String userProfile,
        String password,
        String serviceAccept,
        boolean platformType
) {}