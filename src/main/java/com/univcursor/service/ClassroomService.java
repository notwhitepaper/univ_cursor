package com.univcursor.service;

import com.univcursor.domain.Classroom;
import com.univcursor.domain.repository.ClassroomRepository;
import com.univcursor.web.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ClassroomService {

    private final ClassroomRepository classroomRepository;

    public ClassroomService(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    public List<Classroom> findAll() {
        return classroomRepository.findAll();
    }

    @Transactional
    public Classroom create(String name, String building) {
        if (classroomRepository.existsByName(name)) {
            throw new BusinessException("DUPLICATE_CLASSROOM", "이미 존재하는 강의실입니다.");
        }
        return classroomRepository.save(new Classroom(name, building));
    }

    @Transactional
    public Classroom update(Long id, String name, String building) {
        Classroom classroom = classroomRepository.findById(id)
                .orElseThrow(() -> new BusinessException("CLASSROOM_NOT_FOUND", "강의실을 찾을 수 없습니다."));
        classroom.setName(name);
        classroom.setBuilding(building);
        return classroom;
    }

    @Transactional
    public void delete(Long id) {
        classroomRepository.deleteById(id);
    }
}
