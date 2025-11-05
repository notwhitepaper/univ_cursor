package com.univcursor.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "course_times")
public class CourseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(nullable = false)
    private int dayOfWeek;

    @Column(nullable = false)
    private int startMin;

    @Column(nullable = false)
    private int endMin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    protected CourseTime() {
    }

    public CourseTime(int dayOfWeek, int startMin, int endMin, Classroom classroom) {
        this.dayOfWeek = dayOfWeek;
        this.startMin = startMin;
        this.endMin = endMin;
        this.classroom = classroom;
    }

    public Long getId() {
        return id;
    }

    public Course getCourse() {
        return course;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public int getStartMin() {
        return startMin;
    }

    public int getEndMin() {
        return endMin;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    void setCourse(Course course) {
        this.course = course;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setStartMin(int startMin) {
        this.startMin = startMin;
    }

    public void setEndMin(int endMin) {
        this.endMin = endMin;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }
}
