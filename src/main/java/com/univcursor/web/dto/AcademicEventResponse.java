package com.univcursor.web.dto;

import com.univcursor.domain.AcademicEvent;

import java.time.LocalDate;

public record AcademicEventResponse(
        Long id,
        String title,
        LocalDate date
) {
    public static AcademicEventResponse from(AcademicEvent event) {
        return new AcademicEventResponse(event.getId(), event.getTitle(), event.getDate());
    }
}
