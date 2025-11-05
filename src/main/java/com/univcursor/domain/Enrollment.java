package com.univcursor.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments", uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"}))
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean rolledOver;

    protected Enrollment() {
    }

    public Enrollment(User student, Course course, LocalDateTime createdAt, boolean rolledOver) {
        this.student = student;
        this.course = course;
        this.createdAt = createdAt;
        this.rolledOver = rolledOver;
    }

    public Long getId() {
        return id;
    }

    public User getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isRolledOver() {
        return rolledOver;
    }

    public void setRolledOver(boolean rolledOver) {
        this.rolledOver = rolledOver;
    }
}
