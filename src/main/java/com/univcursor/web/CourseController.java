package com.univcursor.web;

import com.univcursor.service.CourseService;
import com.univcursor.web.dto.CourseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public Page<CourseResponse> list(@RequestParam(value = "q", required = false) String keyword,
                                     @PageableDefault(size = 20) Pageable pageable) {
        return courseService.searchCourses(keyword, pageable).map(CourseResponse::from);
    }

    @GetMapping("/{id}")
    public CourseResponse get(@PathVariable Long id) {
        return CourseResponse.from(courseService.getCourse(id));
    }
}
