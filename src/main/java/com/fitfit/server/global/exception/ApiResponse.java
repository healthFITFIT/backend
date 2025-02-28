package com.fitfit.server.global.exception;

import io.micrometer.common.lang.Nullable;
import org.springframework.http.HttpStatus;

public record ApiResponse<T>(
        boolean success,
        @Nullable String jwt,
        @Nullable T user_data,
        @Nullable ExceptionDto error
) {
    public static <T> ApiResponse<T> ok(@Nullable final T user_data) {
        return new ApiResponse<>(true, null, user_data, null);
    }

    public static <T> ApiResponse<T> created(@Nullable final T user_data) {
        return new ApiResponse<>(true, null, user_data, null);
    }

    public static <T> ApiResponse<T> success(@Nullable final String jwt, @Nullable final T user_data) {
        return new ApiResponse<>(true, jwt, user_data, null);
    }

    public static <T> ApiResponse<T> fail(final CustomException e) {
        return new ApiResponse<>(false, null, null, ExceptionDto.of(e.getErrorCode()));
    }

    public static <T> ApiResponse<T> fail(String message, HttpStatus status) {
        return new ApiResponse<>(false, null, null, new ExceptionDto(status.value(), message));
    }
}