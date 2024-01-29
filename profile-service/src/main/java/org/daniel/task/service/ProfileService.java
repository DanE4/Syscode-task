package org.daniel.task.service;

import org.daniel.task.dto.StudentDTO;
import org.daniel.task.model.Response;

import java.util.List;
import java.util.UUID;

public interface ProfileService {
    List<StudentDTO> getAllStudents();

    StudentDTO createStudent(StudentDTO studentDTO);

    StudentDTO updateStudent(UUID id, StudentDTO studentDTO);

    void deleteStudent(UUID id);

    Response getAddressFromAddressService();
}
