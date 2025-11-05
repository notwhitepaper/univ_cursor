package com.univcursor.domain.repository;

import com.univcursor.domain.RegistrationWindow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationWindowRepository extends JpaRepository<RegistrationWindow, Long> {
    Optional<RegistrationWindow> findTopByOrderByIdDesc();
}
