package com.fitfit.server.global.exception;


import io.micrometer.common.lang.Nullable;
import org.springframework.http.HttpStatus;

public record ApiResponse<T>(
        boolean success,
        @Nullable String jwt_token,
        @Nullable T userData,
        @Nullable ExceptionDto error
) {
    public static <T> ApiResponse<T> ok(@Nullable final T userData) {
        return new ApiResponse<>(true, null, userData, null);
    }

    public static <T> ApiResponse<T> created(@Nullable final T userData) {
        return new ApiResponse<>(true, null, userData, null);
    }

    public static <T> ApiResponse<T> success(@Nullable final String jwt_token, @Nullable final T userData) {
        return new ApiResponse<>(true, jwt_token, userData, null);
    }

    public static <T> ApiResponse<T> fail(final CustomException e) {
        return new ApiResponse<>(false, null, null, ExceptionDto.of(e.getErrorCode()));
    }

    public static <T> ApiResponse<T> fail(String message, HttpStatus status) {
        return new ApiResponse<>(false, null, null, new ExceptionDto(status.value(), message));
    }
}