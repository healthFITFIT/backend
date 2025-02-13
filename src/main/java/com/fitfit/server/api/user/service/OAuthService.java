package com.fitfit.server.api.user.service;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class OAuthService {

    public String getUserEmail(OAuth2User oAuth2User) {
        return oAuth2User.getAttribute("email"); // Google에서 제공하는 이메일 반환
    }
}