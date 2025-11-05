package com.univcursor.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CourseCreateRequest(
        @NotBlank String code,
        @NotBlank String title,
        @NotBlank String dept,
        @Min(1) int credit,
        @Min(1) int capacity,
        @NotEmpty List<CourseTimeRequest> times
) {
}
