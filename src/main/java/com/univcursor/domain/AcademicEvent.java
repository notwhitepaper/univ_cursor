package com.univcursor.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "academic_events")
public class AcademicEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate date;

    protected AcademicEvent() {
    }

    public AcademicEvent(String title, LocalDate date) {
        this.title = title;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDate() {
        return date;
    }
}
