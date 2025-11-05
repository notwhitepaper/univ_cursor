package com.univcursor.web.dto;

import com.univcursor.domain.RegistrationWindow;

import java.time.LocalDateTime;

public record RegistrationWindowResponse(
        Long id,
        LocalDateTime startAt,
        LocalDateTime endAt,
        boolean active,
        boolean rolloverExecuted
) {
    public static RegistrationWindowResponse from(RegistrationWindow window) {
        return new RegistrationWindowResponse(window.getId(), window.getStartAt(), window.getEndAt(), window.isActive(), window.isRolloverExecuted());
    }
}
