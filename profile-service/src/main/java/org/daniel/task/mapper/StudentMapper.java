package org.daniel.task.mapper;


import org.daniel.task.dto.StudentDTO;
import org.daniel.task.entity.Student;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    Student mapToEntity(StudentDTO studentDTO);

    StudentDTO mapToDto(Student student);

    List<StudentDTO> mapToDtoList(List<Student> students);
}
