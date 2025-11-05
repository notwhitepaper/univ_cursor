package com.univcursor.web.dto;

import com.univcursor.domain.Role;
import com.univcursor.domain.User;

public record UserResponse(
        Long id,
        String loginId,
        String name,
        Role role,
        String major,
        int maxCredits
) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getLoginId(), user.getName(), user.getRole(), user.getMajor(), user.getMaxCredits());
    }
}
