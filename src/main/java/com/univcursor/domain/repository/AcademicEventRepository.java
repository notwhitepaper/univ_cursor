package com.univcursor.domain.repository;

import com.univcursor.domain.AcademicEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AcademicEventRepository extends JpaRepository<AcademicEvent, Long> {
    List<AcademicEvent> findByDateBetweenOrderByDateAsc(LocalDate from, LocalDate to);
}
