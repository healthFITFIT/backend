package com.fitfit.server.api.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OAuthService {

    private static final Logger logger = LoggerFactory.getLogger(OAuthService.class);

    @Value("${oauth2.google.client-id}")
    private String clientId;

    @Value("${oauth2.google.client-secret}")
    private String clientSecret;

    @Value("${oauth2.google.redirect-uri}")
    private String redirectUri;

    @Value("${oauth2.google.token-uri}")
    private String TOKEN_URI;

    @Value("${oauth2.google.resource-uri}")
    private String RESOURCE_URI;

    private static final String GOOGLE_OAUTH_URL = "https://accounts.google.com/o/oauth2/v2/auth";
    private static final String SCOPE = "openid profile email";
    private static final String RESPONSE_TYPE = "code";

    @Autowired
    private RestTemplate restTemplate;

    // Google OAuth URL 생성
    public String getGoogleOAuthURL() {
        return UriComponentsBuilder.fromHttpUrl(GOOGLE_OAUTH_URL)
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", RESPONSE_TYPE)
                .queryParam("scope", SCOPE)
                .toUriString();
    }

    // Google Callback 처리 및 access token 발급
    public String processGoogleCallback(String code) {
        logger.info("Processing Google Callback with code: {}", code);

        String requestBody = "code=" + code +
                "&client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&redirect_uri=" + redirectUri +
                "&grant_type=authorization_code";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> tokenResponse = restTemplate.exchange(TOKEN_URI, HttpMethod.POST, entity, String.class);

        String accessToken = null;
        try {
            JsonNode tokenJson = new ObjectMapper().readTree(tokenResponse.getBody());
            if (tokenJson.has("access_token")) {
                accessToken = tokenJson.get("access_token").asText();
            } else {
                throw new RuntimeException("Access token not found in the response.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse access token from Google response.", e);
        }

        String userInfoUrl = UriComponentsBuilder.fromHttpUrl(RESOURCE_URI)
                .queryParam("access_token", accessToken)
                .toUriString();

        ResponseEntity<String> userInfoResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, null, String.class);

        String userInfo = userInfoResponse.getBody();

        return accessToken;
    }

    public String refreshAccessToken(String refreshToken) {
        String requestBody = "client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&refresh_token=" + refreshToken +
                "&grant_type=refresh_token";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                TOKEN_URI, HttpMethod.POST, entity, String.class);

        try {
            JsonNode responseJson = new ObjectMapper().readTree(response.getBody());
            if (responseJson.has("access_token")) {
                return responseJson.get("access_token").asText();
            }
            throw new RuntimeException("Access token not found in response");
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse refresh token response", e);
        }
    }
}