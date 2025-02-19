package com.fitfit.server.api.user;

import com.fitfit.server.api.user.dto.MemberUpdateRequest;
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
    @Column(name="userId")
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

    @Builder
    public Member(String email, String name, String password, String userProfile, boolean serviceAccept, String platformType, String role, LocalDate userRegisteredAt, LocalDate userModifiedAt) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.userProfile = userProfile;
        this.serviceAccept = serviceAccept;
        this.userRegisteredAt = userRegisteredAt != null ? userRegisteredAt : LocalDate.now(); // null이 아니면 userRegisteredAt 사용, 아니면 기본값으로 설정
        this.platformType = platformType != null ? platformType : "google";
        this.role = role != null ? role : "USER";
        this.userModifiedAt = userModifiedAt;
    }

    public void updateUser(MemberUpdateRequest request) {
        if (request.name() != null) {
            this.name = request.name();
        }
        if (request.userProfile() != null) {
            this.userProfile = request.userProfile();
        }
        this.userModifiedAt = LocalDate.now();
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

    // Getter for name
    public String getName() {
        return name;
    }

}