package com.fitfit.server.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor // 생성자 자동 생성
public class UserResponse {
    private Long userId;
    private String email;
    private String name;
    private String role;
}