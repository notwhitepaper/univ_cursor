package com.univcursor.web;

import com.univcursor.service.CourseService;
import com.univcursor.web.dto.CourseCreateRequest;
import com.univcursor.web.dto.CourseResponse;
import com.univcursor.web.dto.CourseTimeRequest;
import com.univcursor.web.dto.CourseUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/courses")
public class AdminCourseController {

    private final CourseService courseService;

    public AdminCourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public CourseResponse create(@Valid @RequestBody CourseCreateRequest request) {
        return CourseResponse.from(courseService.createCourse(request.code(), request.title(), request.dept(), request.credit(), request.capacity(), mapTimes(request.times())));
    }

    @PutMapping("/{id}")
    public CourseResponse update(@PathVariable Long id, @Valid @RequestBody CourseUpdateRequest request) {
        return CourseResponse.from(courseService.updateCourse(id, request.title(), request.dept(), request.credit(), request.capacity(), mapTimes(request.times())));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        courseService.deleteCourse(id);
    }

    private List<CourseService.CourseTimeInput> mapTimes(List<CourseTimeRequest> times) {
        return times.stream()
                .map(t -> new CourseService.CourseTimeInput(t.dayOfWeek(), t.startMin(), t.endMin(), t.classroomId()))
                .toList();
    }
}
