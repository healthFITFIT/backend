package com.fitfit.server.api.user;

import com.fitfit.server.api.user.dto.UserUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column
    private String userProfile;

    @Column(nullable = false)
    private boolean serviceAccept = true; // 기본값 true로 설정

    @Column(nullable = false)
    private LocalDate userRegisteredAt;

    @Column
    private LocalDate userModifiedAt;

    @Column(nullable = false, length = 50)
    private String platformType;

    @Column(nullable = false, length = 50)
    private String role;

    // Builder 패턴을 적용한 생성자
    @Builder
    public Member(String email, String name, String password, String userProfile, boolean serviceAccept, String platformType, String role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.userProfile = userProfile;
        this.serviceAccept = serviceAccept;
        this.userRegisteredAt = LocalDate.now();
        this.platformType = platformType != null ? platformType : "default";
        this.role = role != null ? role : "USER";
    }

    // 기존 정보를 업데이트하는 메서드
    public Member updateUser(UserUpdateRequest request) {
        return Member.builder()
                .email(this.email)
                .name(request.name() != null ? request.name() : this.name)
                .userProfile(request.userProfile() != null ? request.userProfile() : this.userProfile)
                .password(request.password() != null ? request.password() : this.password)
                .serviceAccept(request.serviceAccept() != null ? Boolean.parseBoolean(request.serviceAccept()) : this.serviceAccept)
                .role(this.role)
                .build();
    }

    public String getEmail() {
        return this.email;
    }

    public Long getUserId() {
        return this.userId;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    // Getter for name
    public String getName() {
        return name;
    }

}