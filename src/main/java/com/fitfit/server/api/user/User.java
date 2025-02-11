package com.fitfit.server.api.user;

import com.fitfit.server.api.user.dto.UserUpdateRequest;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 255, unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(length = 255)
    private String userProfile;

    @Column(nullable = false)
    private boolean serviceAccept;

    @Column(nullable = false)
    private LocalDate userRegisteredAt;

    @Column
    private LocalDate userModifiedAt;

    @Column(nullable = false, length = 50)
    private String platformType;

    @Column(nullable = false, length = 50)
    private String role;

    @Builder
    public User(String email, String name, String password, String userProfile, boolean serviceAccept, String platformType, String role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.userProfile = userProfile;
        this.serviceAccept = serviceAccept;
        this.userRegisteredAt = LocalDate.now();
        this.platformType = platformType;
        this.role = role != null ? role : "USER";
    }

    public User updateUser(UserUpdateRequest request) {
        return User.builder()
                .email(this.email)
                .name(request.name() != null ? request.name() : this.name)
                .userProfile(request.userProfile() != null ? request.userProfile() : this.userProfile)
                .password(request.password() != null ? request.password() : this.password)
                .serviceAccept(request.serviceAccept() != null ? request.serviceAccept() : this.serviceAccept)
                .platformType(this.platformType)
                .role(this.role)
                .build();
    }
}