package com.univcursor.service;

import com.univcursor.domain.CartItem;
import com.univcursor.domain.Course;
import com.univcursor.domain.Enrollment;
import com.univcursor.domain.RegistrationWindow;
import com.univcursor.domain.repository.CartItemRepository;
import com.univcursor.domain.repository.CourseRepository;
import com.univcursor.domain.repository.EnrollmentRepository;
import com.univcursor.domain.repository.RegistrationWindowRepository;
import com.univcursor.web.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class RegistrationService {

    private final RegistrationWindowRepository registrationWindowRepository;
    private final CartItemRepository cartItemRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;

    public RegistrationService(RegistrationWindowRepository registrationWindowRepository,
                               CartItemRepository cartItemRepository,
                               EnrollmentRepository enrollmentRepository,
                               CourseRepository courseRepository) {
        this.registrationWindowRepository = registrationWindowRepository;
        this.cartItemRepository = cartItemRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
    }

    public RegistrationWindow getCurrentWindow() {
        return registrationWindowRepository.findTopByOrderByIdDesc()
                .orElseThrow(() -> new BusinessException("WINDOW_NOT_FOUND", "수강신청 기간이 설정되지 않았습니다."));
    }

    public boolean isRegistrationOpen() {
        try {
            RegistrationWindow window = getCurrentWindow();
            LocalDateTime now = LocalDateTime.now();
            return window.isActive() && !now.isBefore(window.getStartAt()) && !now.isAfter(window.getEndAt());
        } catch (BusinessException ex) {
            return false;
        }
    }

    public void assertRegistrationOpen() {
        RegistrationWindow window = getCurrentWindow();
        LocalDateTime now = LocalDateTime.now();
        if (!window.isActive() || now.isBefore(window.getStartAt()) || now.isAfter(window.getEndAt())) {
            throw new BusinessException("REGISTRATION_CLOSED", "현재는 수강신청 기간이 아닙니다.");
        }
    }

    @Transactional
    public RegistrationWindow updateWindow(LocalDateTime startAt, LocalDateTime endAt, boolean active) {
        RegistrationWindow window = registrationWindowRepository.findTopByOrderByIdDesc()
                .orElse(new RegistrationWindow(startAt, endAt, active, false));
        window.update(startAt, endAt, active);
        window.resetRolloverFlag();
        return registrationWindowRepository.save(window);
    }

    @Transactional
    public void triggerRollover() {
        RegistrationWindow window = getCurrentWindow();
        if (!window.isActive()) {
            throw new BusinessException("WINDOW_INACTIVE", "활성화된 수강신청 기간이 아닙니다.");
        }
        if (window.isRolloverExecuted()) {
            throw new BusinessException("ROLLOVER_ALREADY_EXECUTED", "이미 이월이 실행되었습니다.");
        }

        List<Course> courses = courseRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        for (Course course : courses) {
            List<CartItem> cartItems = cartItemRepository.findByCourseId(course.getId());
            if (cartItems.isEmpty()) {
                continue;
            }
            long cartCount = cartItems.size();
            if (course.getCapacity() - course.getEnrolledCount() >= cartCount) {
                for (CartItem cartItem : cartItems) {
                    if (enrollmentRepository.existsByStudentIdAndCourseId(cartItem.getStudent().getId(), course.getId())) {
                        continue;
                    }
                    Enrollment enrollment = new Enrollment(cartItem.getStudent(), course, now, true);
                    enrollmentRepository.save(enrollment);
                    course.increaseEnrolled(1);
                }
                cartItemRepository.deleteAll(cartItems);
            } else {
                cartItemRepository.deleteAll(cartItems);
            }
        }

        window.markRolloverExecuted();
        registrationWindowRepository.save(window);
    }
}
