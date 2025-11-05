package com.univcursor.service;

import com.univcursor.domain.Classroom;
import com.univcursor.domain.Course;
import com.univcursor.domain.CourseTime;
import com.univcursor.domain.repository.ClassroomRepository;
import com.univcursor.domain.repository.CourseRepository;
import com.univcursor.web.exception.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;
    private final ClassroomRepository classroomRepository;

    public CourseService(CourseRepository courseRepository, ClassroomRepository classroomRepository) {
        this.courseRepository = courseRepository;
        this.classroomRepository = classroomRepository;
    }

    public Page<Course> searchCourses(String keyword, Pageable pageable) {
        if (keyword == null || keyword.isBlank()) {
            return courseRepository.findAll(pageable);
        }
        return courseRepository.findByCodeContainingIgnoreCaseOrTitleContainingIgnoreCaseOrDeptContainingIgnoreCase(keyword, keyword, keyword, pageable);
    }

    @Transactional
    public Course createCourse(String code, String title, String dept, int credit, int capacity, List<CourseTimeInput> times) {
        if (courseRepository.existsByCode(code)) {
            throw new BusinessException("DUPLICATE_COURSE", "이미 존재하는 강의 코드입니다.");
        }
        Course course = new Course(code, title, dept, credit, capacity);
        applyTimes(course, times);
        return courseRepository.save(course);
    }

    @Transactional
    public Course updateCourse(Long id, String title, String dept, int credit, int capacity, List<CourseTimeInput> times) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new BusinessException("COURSE_NOT_FOUND", "강의를 찾을 수 없습니다."));
        course.setTitle(title);
        course.setDept(dept);
        course.setCredit(credit);
        course.setCapacity(capacity);
        course.clearTimes();
        applyTimes(course, times);
        return course;
    }

    @Transactional
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new BusinessException("COURSE_NOT_FOUND", "강의를 찾을 수 없습니다.");
        }
        courseRepository.deleteById(id);
    }

    public Course getCourse(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new BusinessException("COURSE_NOT_FOUND", "강의를 찾을 수 없습니다."));
    }

    private void applyTimes(Course course, List<CourseTimeInput> times) {
        for (CourseTimeInput input : times) {
            if (input.startMin() >= input.endMin()) {
                throw new BusinessException("INVALID_TIME", "강의 시간의 시작과 종료가 올바르지 않습니다.");
            }
            Classroom classroom = classroomRepository.findById(input.classroomId())
                    .orElseThrow(() -> new BusinessException("CLASSROOM_NOT_FOUND", "강의실을 찾을 수 없습니다."));
            CourseTime time = new CourseTime(input.dayOfWeek(), input.startMin(), input.endMin(), classroom);
            course.addTime(time);
        }
    }

    public record CourseTimeInput(int dayOfWeek, int startMin, int endMin, Long classroomId) {
    }
}
