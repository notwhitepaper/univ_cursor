package com.univcursor.service;

import com.univcursor.domain.CartItem;
import com.univcursor.domain.Course;
import com.univcursor.domain.Enrollment;
import com.univcursor.domain.User;
import com.univcursor.domain.repository.CartItemRepository;
import com.univcursor.domain.repository.CourseRepository;
import com.univcursor.domain.repository.EnrollmentRepository;
import com.univcursor.support.TimeOverlapUtil;
import com.univcursor.web.exception.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final CartItemRepository cartItemRepository;
    private final RegistrationService registrationService;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             CourseRepository courseRepository,
                             CartItemRepository cartItemRepository,
                             RegistrationService registrationService) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
        this.cartItemRepository = cartItemRepository;
        this.registrationService = registrationService;
    }

    public Page<Enrollment> getEnrollments(Long studentId, Pageable pageable) {
        return enrollmentRepository.findByStudentId(studentId, pageable);
    }

    @Transactional
    public void enroll(User student, Long courseId) {
        registrationService.assertRegistrationOpen();
        if (enrollmentRepository.existsByStudentIdAndCourseId(student.getId(), courseId)) {
            throw new BusinessException("ALREADY_ENROLLED", "이미 수강신청한 강의입니다.");
        }

        Course course = courseRepository.findWithLockingById(courseId)
                .orElseThrow(() -> new BusinessException("COURSE_NOT_FOUND", "강의를 찾을 수 없습니다."));

        if (course.getEnrolledCount() >= course.getCapacity()) {
            throw new BusinessException("COURSE_FULL", "정원이 가득 찼습니다.");
        }

        int enrolledCredits = enrollmentRepository.sumCreditsByStudentId(student.getId());
        if (enrolledCredits + course.getCredit() > student.getMaxCredits()) {
            throw new BusinessException("CREDIT_LIMIT_EXCEEDED", "최대 20학점을 초과합니다.");
        }

        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(student.getId());
        List<CartItem> cartItems = cartItemRepository.findByStudentId(student.getId());
        List<Course> coursesToCheck = Stream.concat(
                enrollments.stream().map(Enrollment::getCourse),
                cartItems.stream().map(CartItem::getCourse)
        ).filter(existing -> !existing.getId().equals(courseId))
                .collect(Collectors.toCollection(ArrayList::new));

        if (TimeOverlapUtil.hasConflict(coursesToCheck, course)) {
            throw new BusinessException("TIME_CONFLICT", "다른 강의와 시간이 중복됩니다.");
        }

        Enrollment enrollment = new Enrollment(student, course, LocalDateTime.now(), false);
        enrollmentRepository.save(enrollment);
        course.increaseEnrolled(1);

        if (cartItemRepository.existsByStudentIdAndCourseId(student.getId(), courseId)) {
            cartItemRepository.deleteByStudentIdAndCourseId(student.getId(), courseId);
        }
    }

    @Transactional
    public void cancel(User student, Long courseId) {
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(student.getId(), courseId)
                .orElseThrow(() -> new BusinessException("ENROLLMENT_NOT_FOUND", "수강신청 내역이 없습니다."));
        Course course = courseRepository.findWithLockingById(courseId)
                .orElseThrow(() -> new BusinessException("COURSE_NOT_FOUND", "강의를 찾을 수 없습니다."));
        enrollmentRepository.delete(enrollment);
        course.decreaseEnrolled(1);
    }
}
