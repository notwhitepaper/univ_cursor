package com.univcursor.web.dto;

import jakarta.validation.constraints.NotBlank;

public record ClassroomRequest(
        @NotBlank String name,
        @NotBlank String building
) {
}
