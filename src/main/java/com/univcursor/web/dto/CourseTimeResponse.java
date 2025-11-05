package com.univcursor.web.dto;

import com.univcursor.domain.CourseTime;

public record CourseTimeResponse(
        int dayOfWeek,
        int startMin,
        int endMin,
        Long classroomId,
        String classroomName,
        String building
) {
    public static CourseTimeResponse from(CourseTime time) {
        return new CourseTimeResponse(
                time.getDayOfWeek(),
                time.getStartMin(),
                time.getEndMin(),
                time.getClassroom() != null ? time.getClassroom().getId() : null,
                time.getClassroom() != null ? time.getClassroom().getName() : null,
                time.getClassroom() != null ? time.getClassroom().getBuilding() : null
        );
    }
}
