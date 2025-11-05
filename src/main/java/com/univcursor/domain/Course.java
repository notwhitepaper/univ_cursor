package com.univcursor.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String code;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String dept;

    @Column(nullable = false)
    private int credit;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private int enrolledCount;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseTime> times = new ArrayList<>();

    protected Course() {
    }

    public Course(String code, String title, String dept, int credit, int capacity) {
        this.code = code;
        this.title = title;
        this.dept = dept;
        this.credit = credit;
        this.capacity = capacity;
    }

    public void addTime(CourseTime time) {
        time.setCourse(this);
        this.times.add(time);
    }

    public void clearTimes() {
        this.times.forEach(time -> time.setCourse(null));
        this.times.clear();
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDept() {
        return dept;
    }

    public int getCredit() {
        return credit;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getEnrolledCount() {
        return enrolledCount;
    }

    public List<CourseTime> getTimes() {
        return times;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setEnrolledCount(int enrolledCount) {
        this.enrolledCount = enrolledCount;
    }

    public void increaseEnrolled(int amount) {
        this.enrolledCount += amount;
    }

    public void decreaseEnrolled(int amount) {
        this.enrolledCount -= amount;
        if (this.enrolledCount < 0) {
            this.enrolledCount = 0;
        }
    }
}
