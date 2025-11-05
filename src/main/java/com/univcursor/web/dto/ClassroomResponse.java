package com.univcursor.web.dto;

import com.univcursor.domain.Classroom;

public record ClassroomResponse(
        Long id,
        String name,
        String building
) {
    public static ClassroomResponse from(Classroom classroom) {
        return new ClassroomResponse(classroom.getId(), classroom.getName(), classroom.getBuilding());
    }
}
