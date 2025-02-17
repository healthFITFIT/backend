package com.fitfit.server.userTest;

import com.fitfit.server.api.user.controller.MemberController;
import com.fitfit.server.api.user.dto.UserSignUpRequest;
import com.fitfit.server.api.user.dto.UserUpdateRequest;
import com.fitfit.server.api.user.service.MemberService;
import com.fitfit.server.global.auth.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private MemberService memberService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new MemberController(memberService, jwtTokenUtil))
                .defaultRequest(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .build();
    }

    @Test
    void signUpTest() throws Exception {
        UserSignUpRequest request = new UserSignUpRequest("test@example.com", "password", "Test User");

        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")  // UTF-8 인코딩 설정
                        .content("{\"email\":\"test@example.com\", \"password\":\"password\", \"name\":\"Test User\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("회원가입이 완료되었습니다."));  // 응답값 확인

        verify(memberService, times(1)).signUp(any(UserSignUpRequest.class));
    }

    @Test
    void updateUserTest() throws Exception {
        String token = "validToken";
        String email = "test@example.com";
        UserUpdateRequest request = new UserUpdateRequest("example@gmail.com", "Updated User", "New Profile", "newpassword123", true);

        when(jwtTokenUtil.extractUsername(token)).thenReturn(email);

        mockMvc.perform(put("/users/update/{userId}", 1L)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content("{\"email\":\"example@gmail.com\", \"name\":\"Updated User\", \"userProfile\":\"New Profile\", \"password\":\"newpassword123\", \"serviceAccept\":true}"))
                .andExpect(status().isOk())
                .andExpect(content().string("회원정보가 수정되었습니다."));

        verify(memberService, times(1)).updateUser(eq(email), any(UserUpdateRequest.class));
    }



    @Test
    void deleteUserTest() throws Exception {
        String token = "validToken";
        String email = "test@example.com";

        when(jwtTokenUtil.extractUsername(token)).thenReturn(email);

        mockMvc.perform(delete("/users/delete/{userId}", 1L)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("회원탈퇴가 완료되었습니다."));

        verify(memberService, times(1)).deleteUser(eq(email));
    }

    @Test
    void logoutTest() throws Exception {
        String token = "validToken";

        mockMvc.perform(post("/users/logout")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("로그아웃 완료되었습니다."));

        verify(memberService, times(1)).logout(eq(token));
    }
}