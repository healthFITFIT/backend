package com.fitfit.server.global.exception;

public class ExceptionDto {
    private final int code;
    private final String message;

    // 기존 생성자 (ErrorCode 사용)
    public ExceptionDto(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    // 새로운 생성자 (int와 String을 사용)
    public ExceptionDto(int code, String message) {
        this.code = code;
        this.message = message;
    }

    // Getter methods (필요에 따라 추가)
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    // ExceptionDto 생성 메서드
    public static ExceptionDto of(ErrorCode errorCode) {
        return new ExceptionDto(errorCode);
    }

    public static ExceptionDto of(int code, String message) {
        return new ExceptionDto(code, message);
    }
}