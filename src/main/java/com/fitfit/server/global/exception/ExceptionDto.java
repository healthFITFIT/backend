package com.fitfit.server.global.exception;

public class ExceptionDto {
    private final int code;
    private final String message;

    public ExceptionDto(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }


    public ExceptionDto(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ExceptionDto of(ErrorCode errorCode) {
        return new ExceptionDto(errorCode);
    }

    public static ExceptionDto of(int code, String message) {
        return new ExceptionDto(code, message);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}