package com.univcursor.web;

import com.univcursor.config.security.UserPrincipal;
import com.univcursor.service.EnrollmentService;
import com.univcursor.web.dto.EnrollmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public Page<EnrollmentResponse> list(@AuthenticationPrincipal UserPrincipal principal,
                                         @RequestParam(value = "priority", required = false) String priority,
                                         @PageableDefault(size = 20) Pageable pageable) {
        Pageable effective = pageable;
        if ("rolledover".equalsIgnoreCase(priority)) {
            effective = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                    Sort.by(Sort.Order.desc("rolledOver"), Sort.Order.desc("createdAt")));
        }
        return enrollmentService.getEnrollments(principal.getUser().getId(), effective).map(EnrollmentResponse::from);
    }

    @PostMapping("/{courseId}")
    public void enroll(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long courseId) {
        enrollmentService.enroll(principal.getUser(), courseId);
    }

    @DeleteMapping("/{courseId}")
    public void cancel(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long courseId) {
        enrollmentService.cancel(principal.getUser(), courseId);
    }
}
