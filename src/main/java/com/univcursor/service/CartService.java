package com.univcursor.service;

import com.univcursor.domain.CartItem;
import com.univcursor.domain.Course;
import com.univcursor.domain.User;
import com.univcursor.domain.repository.CartItemRepository;
import com.univcursor.domain.repository.CourseRepository;
import com.univcursor.support.TimeOverlapUtil;
import com.univcursor.web.exception.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final CourseRepository courseRepository;

    public CartService(CartItemRepository cartItemRepository, CourseRepository courseRepository) {
        this.cartItemRepository = cartItemRepository;
        this.courseRepository = courseRepository;
    }

    public Page<CartItem> getCartItems(Long studentId, Pageable pageable) {
        return cartItemRepository.findByStudentId(studentId, pageable);
    }

    @Transactional
    public void addCourse(User student, Long courseId) {
        if (cartItemRepository.existsByStudentIdAndCourseId(student.getId(), courseId)) {
            throw new BusinessException("ALREADY_IN_CART", "이미 장바구니에 담긴 강의입니다.");
        }
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BusinessException("COURSE_NOT_FOUND", "강의를 찾을 수 없습니다."));

        int currentCredits = cartItemRepository.sumCreditsByStudentId(student.getId());
        if (currentCredits + course.getCredit() > student.getMaxCredits()) {
            throw new BusinessException("CREDIT_LIMIT_EXCEEDED", "최대 20학점을 초과합니다.");
        }

        List<CartItem> cartItems = cartItemRepository.findByStudentId(student.getId());
        if (TimeOverlapUtil.hasConflict(cartItems.stream().map(CartItem::getCourse).toList(), course)) {
            throw new BusinessException("TIME_CONFLICT", "이미 담긴 강의와 시간이 중복됩니다.");
        }

        CartItem cartItem = new CartItem(student, course, LocalDateTime.now());
        cartItemRepository.save(cartItem);
    }

    @Transactional
    public void removeCourse(Long studentId, Long courseId) {
        if (!cartItemRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new BusinessException("CART_ITEM_NOT_FOUND", "장바구니에 없는 강의입니다.");
        }
        cartItemRepository.deleteByStudentIdAndCourseId(studentId, courseId);
    }

    public List<CartItem> getAllForStudent(Long studentId) {
        return cartItemRepository.findByStudentId(studentId);
    }

    @Transactional
    public void clearForStudent(Long studentId) {
        cartItemRepository.deleteByStudentId(studentId);
    }
}
