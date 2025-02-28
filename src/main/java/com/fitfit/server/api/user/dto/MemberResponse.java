package com.fitfit.server.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponse {
    private Long user_id;
    private String email;
    private String name;
    private String role;
    private String user_profile_url;
}