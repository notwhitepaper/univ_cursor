package com.univcursor.web;

import com.univcursor.service.RegistrationService;
import com.univcursor.web.dto.RegistrationWindowRequest;
import com.univcursor.web.dto.RegistrationWindowResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/registration")
public class AdminRegistrationController {

    private final RegistrationService registrationService;

    public AdminRegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PutMapping("/window")
    public RegistrationWindowResponse updateWindow(@Valid @RequestBody RegistrationWindowRequest request) {
        return RegistrationWindowResponse.from(registrationService.updateWindow(request.startAt(), request.endAt(), request.active()));
    }

    @PostMapping("/rollover")
    public void rollover() {
        registrationService.triggerRollover();
    }
}
