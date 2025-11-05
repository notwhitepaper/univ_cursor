package com.univcursor.web;

import com.univcursor.service.ClassroomService;
import com.univcursor.web.dto.ClassroomRequest;
import com.univcursor.web.dto.ClassroomResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/classrooms")
public class AdminClassroomController {

    private final ClassroomService classroomService;

    public AdminClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping
    public List<ClassroomResponse> list() {
        return classroomService.findAll().stream().map(ClassroomResponse::from).toList();
    }

    @PostMapping
    public ClassroomResponse create(@Valid @RequestBody ClassroomRequest request) {
        return ClassroomResponse.from(classroomService.create(request.name(), request.building()));
    }

    @PutMapping("/{id}")
    public ClassroomResponse update(@PathVariable Long id, @Valid @RequestBody ClassroomRequest request) {
        return ClassroomResponse.from(classroomService.update(id, request.name(), request.building()));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        classroomService.delete(id);
    }
}
