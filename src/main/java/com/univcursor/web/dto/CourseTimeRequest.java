package com.univcursor.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CourseTimeRequest(
        @Min(1) @Max(7) int dayOfWeek,
        @Min(0) int startMin,
        @Min(0) int endMin,
        @NotNull Long classroomId
) {
}
