package org.daniel.task;

import org.daniel.task.dto.StudentDTO;
import org.daniel.task.entity.Student;
import org.daniel.task.exception.ResourceNotFoundException;
import org.daniel.task.mapper.StudentMapper;
import org.daniel.task.repository.ProfileRepository;
import org.daniel.task.service.ProfileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProfileServiceUnitTests {

    @Mock
    private StudentMapper studentMapper;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private WebClient webClient;

    @InjectMocks
    private ProfileServiceImpl profileService;

    private StudentDTO studentDTO;
    private Student student;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        webClient = WebClient.builder().build();

        studentDTO = StudentDTO.builder()
                .name("John Doe")
                .email("john.doe@gmail.com")
                .build();

        student = Student.builder()
                .id(UUID.randomUUID())
                .name("John Doe")
                .email("john.doe@gmail.com")
                .build();
    }

    @Test
    public void test_return_list_of_studentDTOS_when_students_exist() {
        // Arrange
        List<Student> students = new ArrayList<>();
        students.add(new Student(UUID.randomUUID(), "John Doe", "john.doe@gmail.com"));
        students.add(new Student(UUID.randomUUID(), "Jane Smith", "jane.smith@gmail.com"));
        // Mocking the profileRepository to return the list of students
        when(profileRepository.findAll()).thenReturn(students);

        // Act
        List<StudentDTO> result = profileService.getAllStudents();

        // Assert
        // Assuming you have a way to compare lists of StudentDTO, you can use assertEquals
        assertEquals(students.size(), result.size());

        // Verify that profileRepository.findAll() was called
        verify(profileRepository, times(1)).findAll();
    }

    @Test
    public void test_return_empty_list_of_studentDTOS_when_no_students_exist() {
        // Arrange
        List<Student> students = new ArrayList<>();

        // Mocking the profileRepository to return the list of students
        when(profileRepository.findAll()).thenReturn(students);

        // Act
        List<StudentDTO> result = profileService.getAllStudents();

        // Assert
        // Assuming you have a way to compare lists of StudentDTO, you can use assertEquals
        assertEquals(students.size(), result.size());

        // Verify that profileRepository.findAll() was called
        verify(profileRepository, times(1)).findAll();
    }

    @Test
    public void should_create_student_with_valid_name_and_email() {
        // Arrange
        UUID id = UUID.randomUUID();
        StudentDTO studentDTO = StudentDTO.builder()
                .name("John Doe")
                .email("john.doe@gmail.com")
                .build();

        Student mappedStudent = Student.builder()
                .id(id)
                .name("John Doe")
                .email("john.doe@gmail.com")
                .build();

        // Mocking setup
        when(studentMapper.mapToEntity(studentDTO)).thenReturn(mappedStudent);
        when(profileRepository.save(mappedStudent)).thenReturn(mappedStudent);
        when(studentMapper.mapToDto(mappedStudent)).thenReturn(studentDTO);

        // Act
        StudentDTO actualStudentDTO = profileService.createStudent(studentDTO);

        // Assert
        assertThat(actualStudentDTO)
                .isNotNull()
                .isEqualTo(studentDTO);

        // Additional assertions
        verify(studentMapper).mapToEntity(studentDTO);
        verify(profileRepository).save(mappedStudent);
        verify(studentMapper).mapToDto(mappedStudent);
    }

    @Test
    public void should_update_student_with_valid_name_And_email() {
        // Arrange
        UUID id = UUID.randomUUID();

        Student mappedStudent = Student.builder()
                .id(id)
                .name("John Doe")
                .email("john.doe@gmail.com")
                .build();

        Student updatedStudent = Student.builder()
                .id(id)
                .name("John Doe")
                .email("john.doe@gmail.com")
                .build();

        // Mocking setup
        when(profileRepository.findById(id)).thenReturn(java.util.Optional.of(mappedStudent));
        when(studentMapper.mapToEntity(studentDTO)).thenReturn(updatedStudent);
        when(profileRepository.save(updatedStudent)).thenReturn(updatedStudent);
        when(studentMapper.mapToDto(updatedStudent)).thenReturn(studentDTO);

        // Act
        StudentDTO actual = profileService.updateStudent(id, studentDTO);

        // Assert
        assertThat(actual)
                .isNotNull()
                .isEqualTo(studentDTO);

        // Additional assertions
        verify(profileRepository).findById(id);
        verify(studentMapper).mapToEntity(studentDTO);
        verify(profileRepository).save(updatedStudent);
        verify(studentMapper).mapToDto(updatedStudent);
    }

    @Test
    public void test_update_student_with_nonexistent_id() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Mocking setup
        when(profileRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> profileService.updateStudent(id, studentDTO));

        // Additional assertions
        verify(profileRepository).findById(id);
    }

    @Test
    public void testNoExceptionsThrownDeleteStudent() {
        // Given
        UUID id = UUID.randomUUID();

        // Mock the findById method to return an Optional containing a student
        when(profileRepository.findById(id)).thenReturn(Optional.of(new Student()));

        // When / Then
        assertDoesNotThrow(() -> profileService.deleteStudent(id), "Exception thrown during deleteStudent");

        // Verify that findById and deleteById were called with the correct ID
        verify(profileRepository, times(1)).findById(id);
        verify(profileRepository, times(1)).deleteById(id);
    }

    @Test
    public void test_throws_resource_not_found_exception_when_student_not_found() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Mock the findById method to return an empty Optional
        when(profileRepository.findById(id)).thenReturn(Optional.empty());
        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> profileService.deleteStudent(id));
    }
}


