package com.fitfit.server.api.user.dto;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest (
        @NotBlank @Email String email,
        @NotBlank @Size(min = 2, max = 255) String name,
        @NotBlank @Size(min = 8, max = 255) String password,
        String userProfile,
        boolean serviceAccept,
        @NotBlank String platformType
) {}