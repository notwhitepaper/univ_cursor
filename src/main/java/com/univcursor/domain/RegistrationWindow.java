package com.univcursor.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "registration_windows")
public class RegistrationWindow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime startAt;

    @Column(nullable = false)
    private LocalDateTime endAt;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private boolean rolloverExecuted;

    protected RegistrationWindow() {
    }

    public RegistrationWindow(LocalDateTime startAt, LocalDateTime endAt, boolean active, boolean rolloverExecuted) {
        this.startAt = startAt;
        this.endAt = endAt;
        this.active = active;
        this.rolloverExecuted = rolloverExecuted;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isRolloverExecuted() {
        return rolloverExecuted;
    }

    public void update(LocalDateTime startAt, LocalDateTime endAt, boolean active) {
        this.startAt = startAt;
        this.endAt = endAt;
        this.active = active;
    }

    public void markRolloverExecuted() {
        this.rolloverExecuted = true;
    }

    public void resetRolloverFlag() {
        this.rolloverExecuted = false;
    }
}
