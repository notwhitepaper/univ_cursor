package com.univcursor.service;

import com.univcursor.domain.AcademicEvent;
import com.univcursor.domain.repository.AcademicEventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AcademicCalendarService {

    private final AcademicEventRepository academicEventRepository;

    public AcademicCalendarService(AcademicEventRepository academicEventRepository) {
        this.academicEventRepository = academicEventRepository;
    }

    public List<AcademicEvent> getEvents(LocalDate from, LocalDate to) {
        return academicEventRepository.findByDateBetweenOrderByDateAsc(from, to);
    }
}
