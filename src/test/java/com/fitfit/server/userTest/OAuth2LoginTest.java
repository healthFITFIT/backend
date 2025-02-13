package com.fitfit.server.userTest;

import com.fitfit.server.global.auth.JwtTokenUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.OAuth2LoginRequestPostProcessor;
import org.springframework.test.web.servlet.MockMvc;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OAuth2LoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;


    @Test
    public void OAuth2_로그인_성공() throws Exception {
        // OAuth2User 모킹
        OAuth2User oauth2User = Mockito.mock(OAuth2User.class);
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("email", "test@gmail.com");
        Mockito.when(oauth2User.getAttribute("email")).thenReturn("test@gmail.com");

        Authentication authentication = new OAuth2AuthenticationToken(oauth2User, List.of(new SimpleGrantedAuthority("ROLE_USER")), "google");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(get("/oauth/success"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").exists())
                .andExpect(jsonPath("$.data.message").value("로그인 성공"));
    }


    @Test
    public void OAuth2_로그인_실패_이메일_없음() throws Exception {
        // Mock OAuth2User with missing email attribute
        OAuth2User oauth2User = Mockito.mock(OAuth2User.class);
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", "testUser");
        Mockito.when(oauth2User.getAttributes()).thenReturn(attributes);

        Authentication authentication = new OAuth2AuthenticationToken(oauth2User, List.of(new SimpleGrantedAuthority("ROLE_USER")), "google");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(get("/oauth/success"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.message").value("이메일을 찾을 수 없습니다."));  // 이메일이 없을 때의 메시지
    }

}