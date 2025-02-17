package com.fitfit.server.userTest;

import com.fitfit.server.api.user.service.IdTokenVerify;
import com.fitfit.server.api.user.service.OAuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;

import java.security.GeneralSecurityException;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OAuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IdTokenVerify idTokenVerify;

    @MockBean
    private OAuthService oAuthService;

    @Test
    void validateTokenTest() throws Exception {
        User user = new User("testUser", "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(user, "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))));
        SecurityContextHolder.setContext(securityContext);

        mockMvc.perform(post("/oauth/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("validToken"))
                .andExpect(status().isOk());
    }

    @Test
    public void validateTokenTest_unauthorized() throws Exception {
        String invalidToken = "invalidToken";

        mockMvc.perform(post("/oauth/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidToken))
                .andExpect(jsonPath("$.data").value("Token is valid"));
    }
    @Test
    void validateTokenTest_internalError() throws Exception {
        String validToken = "validToken";

        when(idTokenVerify.authenticateUser(validToken)).thenThrow(new GeneralSecurityException("Security error"));

        mockMvc.perform(post("/oauth/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"" + validToken + "\""))
                .andExpect(jsonPath("$.data").value("Token is valid"));
    }
}