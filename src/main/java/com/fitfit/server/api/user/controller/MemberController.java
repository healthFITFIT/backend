package com.fitfit.server.api.user.controller;

import com.fitfit.server.api.user.dto.UserResponse;
import com.fitfit.server.api.user.dto.UserSignUpRequest;
import com.fitfit.server.api.user.dto.UserUpdateRequest;
import com.fitfit.server.api.user.Member;
import com.fitfit.server.api.user.service.MemberService;
import com.fitfit.server.global.auth.JwtTokenUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenUtil jwtTokenUtil;

    // 회원가입 (회원 정보를 생성)
    @PostMapping("/signup")
    public ResponseEntity<Member> registerUser(@RequestBody @Valid UserSignUpRequest request) {
        Member member = memberService.signUp(request);
        return ResponseEntity.ok(member);
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserResponse> getUserDetails(@PathVariable String email) {
        Member member = memberService.findMemberByEmail(email);

        UserResponse userResponse = new UserResponse(
                member.getUserId(),
                member.getEmail(),
                member.getName(),
                member.getRole()
        );
        return ResponseEntity.ok(userResponse);
    }

    // 회원 정보 업데이트
    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId,
                                             @Valid @RequestBody UserUpdateRequest request,
                                             @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");
        String email = jwtTokenUtil.extractUsername(token);
        if (email == null) {
            return ResponseEntity.badRequest().body("이메일을 추출할 수 없습니다.");
        }

        memberService.updateUser(email, request);
        return ResponseEntity.ok("회원정보가 수정되었습니다.");
    }

    // 회원 탈퇴
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId,
                                             @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        String email = jwtTokenUtil.extractUsername(token);
        if (email == null) {
            return ResponseEntity.badRequest().body("이메일을 추출할 수 없습니다.");
        }

        memberService.deleteUser(email);
        return ResponseEntity.ok("회원탈퇴가 완료되었습니다.");
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        String result = memberService.logout(token);
        return ResponseEntity.ok("로그아웃 완료되었습니다.");
    }
}