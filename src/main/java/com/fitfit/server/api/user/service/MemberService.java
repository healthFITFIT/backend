package com.fitfit.server.api.user.service;

import com.fitfit.server.api.user.dto.UserSignUpRequest;
import com.fitfit.server.api.user.dto.UserUpdateRequest;
import com.fitfit.server.api.user.repository.MemberRepository;
import com.fitfit.server.api.user.Member;
import com.fitfit.server.global.auth.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    public void signUp(UserSignUpRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        Member newUser = Member.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .name(request.name())
                .build();

        memberRepository.save(newUser);
    }

    public void updateUser(String email, UserUpdateRequest request) {
        Member user = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Member updatedUser = user.updateUser(request);
        memberRepository.save(updatedUser);
    }

    public void deleteUser(String email) {
        Member user = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        memberRepository.delete(user);
    }

    public String logout(String token) {
        return "로그아웃 완료";
    }
    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}