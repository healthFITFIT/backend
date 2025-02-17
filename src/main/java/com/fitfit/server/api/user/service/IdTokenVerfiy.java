package com.fitfit.server.api.user.service;

import com.fitfit.server.global.auth.JwtTokenUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class IdTokenVerfiy {

    private final JwtTokenUtil jwtTokenUtil;
    private final GoogleIdTokenVerifier googleIdTokenVerifier;

    @Value("${oauth2.google.client-id}")
    private String clientId;

    public Map<String, Object> authenticateUser(String idToken) throws GeneralSecurityException, IOException {
        GoogleIdToken googleIdToken;
        try {
            googleIdToken = googleIdTokenVerifier.verify(idToken);
        } catch (GeneralSecurityException | IOException e) {
            log.error("ID token 확인 에러", e);
            throw e;
        }

        if (googleIdToken != null) {
            GoogleIdToken.Payload payload = googleIdToken.getPayload();
            String userId = payload.getSubject();
            String email = payload.getEmail();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");

            log.info("Google OAuth: email = {}", email);

            String accessToken = jwtTokenUtil.generateToken(email);

            Map<String, Object> userDetails = new HashMap<>();
            userDetails.put("userId", userId);
            userDetails.put("email", email);
            userDetails.put("name", name);
            userDetails.put("pictureUrl", pictureUrl);
            userDetails.put("accessToken", accessToken);

            return userDetails;
        } else {
            throw new IllegalArgumentException(" 유효하지 않은 ID token 입니다.");
        }
    }
}