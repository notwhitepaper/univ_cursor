package com.univcursor.domain.repository;

import com.univcursor.domain.Enrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    @EntityGraph(attributePaths = {"course", "course.times", "course.times.classroom"})
    Page<Enrollment> findByStudentId(Long studentId, Pageable pageable);

    @EntityGraph(attributePaths = {"course", "course.times"})
    List<Enrollment> findByStudentId(Long studentId);

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    Optional<Enrollment> findByStudentIdAndCourseId(Long studentId, Long courseId);

    void deleteByStudentIdAndCourseId(Long studentId, Long courseId);

    @Query("select coalesce(sum(e.course.credit), 0) from Enrollment e where e.student.id = :studentId")
    int sumCreditsByStudentId(Long studentId);

    long countByCourseId(Long courseId);
}
