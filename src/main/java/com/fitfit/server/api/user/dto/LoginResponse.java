package com.fitfit.server.api.user.dto;

public record LoginResponse(
        String token,
        String message
) {}