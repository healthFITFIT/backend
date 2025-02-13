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
        GoogleIdToken googleIdToken = googleIdTokenVerifier.verify(idToken);  // 직접 생성하지 않고 주입받은 verifier 사용
        if (googleIdToken != null) {
            GoogleIdToken.Payload payload = googleIdToken.getPayload();
            String userId = payload.getSubject();
            String email = payload.getEmail();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            log.info(email);

            String accessToken = jwtTokenUtil.generateToken(email);

            Map<String, Object> userDetails = new HashMap<>();
            userDetails.put("userId", userId);
            userDetails.put("email", email);
            userDetails.put("name", name);
            userDetails.put("pictureUrl", pictureUrl);
            userDetails.put("accessToken", accessToken);

            return userDetails;
        } else {
            throw new IllegalArgumentException("Invalid ID token.");
        }
    }
}