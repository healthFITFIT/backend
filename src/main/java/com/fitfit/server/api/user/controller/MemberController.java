package com.fitfit.server.api.user.controller;

import com.fitfit.server.api.user.dto.UserResponse;
import com.fitfit.server.api.user.dto.UserSignUpRequest;
import com.fitfit.server.api.user.dto.UserUpdateRequest;
import com.fitfit.server.api.user.service.MemberService;
import com.fitfit.server.global.auth.JwtTokenUtil;
import com.fitfit.server.global.exception.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenUtil jwtTokenUtil;

    // 회원가입 (회원 정보를 생성)
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody @Valid UserSignUpRequest request) {
        memberService.signUp(request);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    // 회원 정보 조회 (로그인한 사용자만 자신의 정보 조회 가능)
    @GetMapping("/userdetail")
    public ResponseEntity<ApiResponse<UserResponse>> getUserDetails(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        String email = jwtTokenUtil.extractUsername(token);
        if (email == null) {
            return ResponseEntity.badRequest().body(ApiResponse.fail("이메일을 추출할 수 없습니다.", HttpStatus.BAD_REQUEST));
        }

        try {
            UserResponse userResponse = memberService.getUserDetails(email);
            return ResponseEntity.ok(ApiResponse.success(userResponse));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail("사용자를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST));
        }
    }

    // 회원 정보 업데이트 (로그인한 사용자만 자신의 정보 수정 가능)
    @PatchMapping("/update")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserUpdateRequest request,
                                             @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");
        String email = jwtTokenUtil.extractUsername(token);
        if (email == null) {
            return ResponseEntity.badRequest().body("이메일을 추출할 수 없습니다.");
        }

        memberService.updateUser(email, request);
        return ResponseEntity.ok("회원정보가 수정되었습니다.");
    }

    // 회원 탈퇴 (로그인한 사용자만 탈퇴 가능)
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestHeader("Authorization") String authorizationHeader) {
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
        memberService.logout(token);
        return ResponseEntity.ok("로그아웃 완료되었습니다.");
    }
}