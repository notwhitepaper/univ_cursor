package com.univcursor.web.dto;

import com.univcursor.domain.Course;

import java.util.List;

public record CourseResponse(
        Long id,
        String code,
        String title,
        String dept,
        int credit,
        int capacity,
        int enrolledCount,
        List<CourseTimeResponse> times
) {
    public static CourseResponse from(Course course) {
        return new CourseResponse(
                course.getId(),
                course.getCode(),
                course.getTitle(),
                course.getDept(),
                course.getCredit(),
                course.getCapacity(),
                course.getEnrolledCount(),
                course.getTimes().stream().map(CourseTimeResponse::from).toList()
        );
    }
}
