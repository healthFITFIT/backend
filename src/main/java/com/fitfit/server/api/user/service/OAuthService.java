package com.fitfit.server.api.user.service;

import com.fitfit.server.api.user.Member;
import com.fitfit.server.api.user.repository.MemberRepository;
import com.fitfit.server.global.auth.JwtTokenUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuthService {

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final NetHttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    private final JwtTokenUtil jwtTokenUtil;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${oauth2.google.client-id}")
    private String clientId;

    @Value("${oauth2.google.client-secret}")
    private String clientSecret;

    @Value("${oauth2.google.redirect-uri}")
    private String redirectUri;

    public Map<String, Object> authenticateUser(String idToken) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(HTTP_TRANSPORT, JSON_FACTORY)
                .setAudience(Collections.singletonList(clientId))
                .build();

        GoogleIdToken googleIdToken = verifier.verify(idToken);
        if (googleIdToken != null) {
            GoogleIdToken.Payload payload = googleIdToken.getPayload();
            String email = payload.getEmail();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");

            log.info("Google OAuth: email = {}", email);

            // 이메일로 회원 조회
            Member member = memberRepository.findByEmail(email)
                    .orElseGet(() -> registerNewUser(email, name, pictureUrl)); // 비회원인 경우 회원가입

            // JWT 토큰 발급
            String jwtToken = jwtTokenUtil.generateToken(email);

            Map<String, Object> userDetails = new HashMap<>();
            userDetails.put("userId", member.getUserId());
            userDetails.put("email", email);
            userDetails.put("name", name);
            userDetails.put("pictureUrl", pictureUrl);
            userDetails.put("jwt_token", jwtToken);

            return userDetails;
        } else {
            throw new IllegalArgumentException("Invalid ID token.");
        }
    }

    /**
     * 새로운 사용자 등록
     */
    private Member registerNewUser(String email, String name, String pictureUrl) {
        // 새 사용자 생성
        Member newUser = Member.builder()
                .email(email)
                .name(name)
                .password(passwordEncoder.encode("defaultPassword")) // 기본 패스워드 설정
                .userProfile(pictureUrl)
                .serviceAccept(true)
                .platformType("google")
                .role("USER")
                .build();
        return memberRepository.save(newUser);
    }

    public Map<String, Object> getAccessTokenFromAuthCode(String authCode) throws IOException {
        log.info("clientId: {}", clientId);
        log.info("clientSecret: {}", clientSecret);

        GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                HTTP_TRANSPORT, JSON_FACTORY,
                clientId, clientSecret,
                authCode, redirectUri)
                .execute();

        Map<String, Object> response = new HashMap<>();
        response.put("access_token", tokenResponse.getAccessToken());
        response.put("id_token", tokenResponse.getIdToken());
        response.put("refresh_token", tokenResponse.getRefreshToken());
        response.put("expires_in", tokenResponse.getExpiresInSeconds());

        return response;
    }
}