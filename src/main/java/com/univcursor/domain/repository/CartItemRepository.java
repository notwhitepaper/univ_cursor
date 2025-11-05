package com.univcursor.domain.repository;

import com.univcursor.domain.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @EntityGraph(attributePaths = {"course", "course.times", "course.times.classroom"})
    Page<CartItem> findByStudentId(Long studentId, Pageable pageable);

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    void deleteByStudentIdAndCourseId(Long studentId, Long courseId);

    @Query("select coalesce(sum(ci.course.credit), 0) from CartItem ci where ci.student.id = :studentId")
    int sumCreditsByStudentId(Long studentId);

    @EntityGraph(attributePaths = {"course", "course.times"})
    List<CartItem> findByStudentId(Long studentId);

    @EntityGraph(attributePaths = {"course", "course.times"})
    List<CartItem> findByCourseId(Long courseId);

    long countByCourseId(Long courseId);

    void deleteByStudentId(Long studentId);
}
