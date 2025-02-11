package com.fitfit.server.api.user.controller;

import com.fitfit.server.api.user.dto.LoginResponse;
import com.fitfit.server.global.auth.JwtTokenUtil;
import com.fitfit.server.global.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OAuthController {

    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping("/success")
    public ResponseEntity<ApiResponse<LoginResponse>> handleOAuth2Success(@AuthenticationPrincipal OAuth2User oauth2User) {
        String email = oauth2User.getAttribute("email");

        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.fail("이메일을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST));
        }

        String jwtToken = jwtTokenUtil.generateToken(email);
        return ResponseEntity.ok(ApiResponse.ok(new LoginResponse(jwtToken, "로그인 성공")));
    }
}