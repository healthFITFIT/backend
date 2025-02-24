package com.fitfit.server.api.user.controller;

import com.fitfit.server.api.user.dto.UserData;
import com.fitfit.server.api.user.service.OAuthService;
import com.fitfit.server.global.exception.ApiResponse;
import com.fitfit.server.global.exception.CustomException;
import com.fitfit.server.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    private final OAuthService oAuthService;

    @PostMapping("/validate")
    public ResponseEntity<ApiResponse<?>> validateIdToken(@RequestBody Map<String, String> request) {
        String idToken = request.get("idToken");
        try {
            Map<String, Object> userDetails = oAuthService.authenticateUser(idToken);
            String jwtToken = (String) userDetails.get("jwt_token");
            UserData userData = (UserData) userDetails.get("userData");

            return ResponseEntity.ok(ApiResponse.success(jwtToken, userData));
        } catch (IllegalArgumentException e) {
            ApiResponse<?> response = ApiResponse.fail(new CustomException(ErrorCode.UNAUTHORIZED, "승인되지 않은 접근입니다."));
            return ResponseEntity.status(ErrorCode.UNAUTHORIZED.getHttpStatus()).body(response);
        } catch (Exception e) {
            ApiResponse<?> response = ApiResponse.fail(new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."));
            return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus()).body(response);
        }
    }


    @PostMapping("/token")
    public ResponseEntity<?> getAccessToken(@RequestBody String accessToken) {
        try {
            Map<String, Object> tokenResponse = oAuthService.getAccessTokenFromAuthCode(accessToken);
            return ResponseEntity.ok(tokenResponse);
        } catch (IOException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "Access token error: " + e.getMessage());
        }
    }
}