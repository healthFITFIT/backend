package com.fitfit.server.api.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest (
        @NotBlank @Email String email,
        @NotBlank @Size(min = 2, max = 255) String name,
        String userProfile,
        String password,
        Boolean serviceAccept
) {
}