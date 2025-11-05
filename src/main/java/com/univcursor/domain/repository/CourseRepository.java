package com.univcursor.domain.repository;

import com.univcursor.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import jakarta.persistence.LockModeType;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @EntityGraph(attributePaths = {"times", "times.classroom"})
    Page<Course> findByCodeContainingIgnoreCaseOrTitleContainingIgnoreCaseOrDeptContainingIgnoreCase(
            String code, String title, String dept, Pageable pageable);

    @EntityGraph(attributePaths = {"times", "times.classroom"})
    Page<Course> findAll(Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Course> findWithLockingById(Long id);

    boolean existsByCode(String code);
}
