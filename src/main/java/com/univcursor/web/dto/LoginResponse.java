package com.univcursor.web.dto;

public record LoginResponse(UserResponse user) {
    public static LoginResponse from(UserResponse user) {
        return new LoginResponse(user);
    }
}
