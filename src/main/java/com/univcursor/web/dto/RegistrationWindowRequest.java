package com.univcursor.web.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RegistrationWindowRequest(
        @NotNull LocalDateTime startAt,
        @NotNull LocalDateTime endAt,
        boolean active
) {
}
