package com.fitfit.server.api.user.dto;

import lombok.Builder;

@Builder
public record UserUpdateRequest(String name, String userProfile){

}