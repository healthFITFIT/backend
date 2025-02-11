package com.fitfit.server.api.user.service;

import com.fitfit.server.api.user.dto.UserUpdateRequest;
import com.fitfit.server.api.user.repository.UserRepository;
import com.fitfit.server.api.user.User;
import com.fitfit.server.global.auth.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;

    public void updateUser(String email, UserUpdateRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        User updatedUser = user.updateUser(request);
        userRepository.save(updatedUser);
    }

    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        userRepository.delete(user);
    }

    public String logout(String token) {
        // 로그아웃 로직 처리 (예: 블랙리스트에 추가 등)
        return "로그아웃 완료";
    }
}