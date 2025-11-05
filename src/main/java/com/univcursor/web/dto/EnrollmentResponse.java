package com.univcursor.web.dto;

import com.univcursor.domain.Enrollment;

import java.time.LocalDateTime;

public record EnrollmentResponse(
        Long courseId,
        CourseResponse course,
        LocalDateTime createdAt,
        boolean rolledOver
) {
    public static EnrollmentResponse from(Enrollment enrollment) {
        return new EnrollmentResponse(
                enrollment.getCourse().getId(),
                CourseResponse.from(enrollment.getCourse()),
                enrollment.getCreatedAt(),
                enrollment.isRolledOver()
        );
    }
}
