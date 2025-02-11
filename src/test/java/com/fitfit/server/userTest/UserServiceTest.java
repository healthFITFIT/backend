package com.fitfit.server.userTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitfit.server.api.user.controller.UserController;
import com.fitfit.server.api.user.dto.UserUpdateRequest;
import com.fitfit.server.api.user.service.UserService;
import com.fitfit.server.api.user.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.doNothing;

@WebMvcTest(UserController.class)
public class UserServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks // @InjectMocks 추가
    private UserController userController;

    @Test
    public void testController() {
        assertNotNull(userController); // UserController가 제대로 주입되었는지 확인
    }

    @Test
    public void 회원정보수정_성공() throws Exception {
        // Given
        String email = "test@example.com";
        UserUpdateRequest updateRequest = new UserUpdateRequest(
                "test@example.com", "New Name", "newProfile.jpg", "newPassword123", true
        );

        User updatedUser = new User("test@example.com", "New Name", "newPassword123", "newProfile.jpg", true, "Platform", "USER");

        doNothing().when(userService).updateUser(email, updateRequest); // updateUser 모의 처리

        // When & Then: 회원정보 수정 요청
        mockMvc.perform(put("/users/{email}/update", email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateRequest)))
                .andExpect(status().isOk());
    }
}