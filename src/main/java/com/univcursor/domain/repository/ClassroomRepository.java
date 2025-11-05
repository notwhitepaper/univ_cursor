package com.univcursor.domain.repository;

import com.univcursor.domain.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    boolean existsByName(String name);
}
