package org.daniel.task;


import org.daniel.task.controller.ProfileController;
import org.daniel.task.dto.StudentDTO;
import org.daniel.task.exception.ResourceNotFoundException;
import org.daniel.task.model.Response;
import org.daniel.task.service.ProfileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProfileControllerUnitTests {

    @Mock
    private ProfileServiceImpl profileService;

    @InjectMocks
    private ProfileController profileController;

    @BeforeEach
    public void setup() {
        profileService = mock(ProfileServiceImpl.class);
        profileController = new ProfileController(profileService);
    }

    @Test
    public void test_getAllStudents_returns_ok() {
        // Arrange
        List<StudentDTO> studentDTOList = new ArrayList<>();
        studentDTOList.add(new StudentDTO("John Doe", "john.doe@gmail.com"));
        studentDTOList.add(new StudentDTO("Jane Smith", "jane.smith@gmail.com"));

        // Mocking the profileService to return the list of students
        when(profileService.getAllStudents()).thenReturn(studentDTOList);

        // Act
        ResponseEntity<Response> responseEntity = profileController.getAllStudents();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals(studentDTOList, responseEntity.getBody().getData());

        // Verify that profileService.getAllStudents() was called
        verify(profileService, times(1)).getAllStudents();
    }

    @Test
    public void test_getAllStudents_handles_exception() {
        // Arrange
        // Mocking the profileService to throw an exception
        when(profileService.getAllStudents()).thenThrow(new RuntimeException("Test exception"));

        // Act
        ResponseEntity<Response> responseEntity = profileController.getAllStudents();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getBody().getStatus());
        assertEquals("Internal server error", responseEntity.getBody().getError());

        // Verify that profileService.getAllStudents() was called
        verify(profileService, times(1)).getAllStudents();
    }

    @Test
    public void test_createStudent_returns_created() {
        // Arrange
        StudentDTO studentDTO = new StudentDTO("John Doe", "john.doe@gmail.com");

        // Mocking the profileService to return the created student
        when(profileService.createStudent(studentDTO)).thenReturn(studentDTO);

        // Act
        ResponseEntity<Response> responseEntity = profileController.createStudent(studentDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(HttpStatus.CREATED.value(), responseEntity.getBody().getStatus());
        assertEquals(studentDTO, responseEntity.getBody().getData());

        // Verify that profileService.createStudent() was called
        verify(profileService, times(1)).createStudent(studentDTO);
    }

    @Test
    public void test_createStudent_handles_conflict() {
        // Arrange
        StudentDTO studentDTO = new StudentDTO("John Doe", "john.doe@gmail.com");

        // Mocking the profileService to throw DataIntegrityViolationException
        when(profileService.createStudent(studentDTO)).thenThrow(new DataIntegrityViolationException("Email already exists"));

        // Act
        ResponseEntity<Response> responseEntity = profileController.createStudent(studentDTO);

        // Assert
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals(HttpStatus.CONFLICT.value(), responseEntity.getBody().getStatus());
        assertEquals("Student with this email already exists", responseEntity.getBody().getError());

        // Verify that profileService.createStudent() was called
        verify(profileService, times(1)).createStudent(studentDTO);
    }

    @Test
    public void test_createStudent_handles_internal_server_error() {
        // Arrange
        StudentDTO studentDTO = new StudentDTO("John Doe", "john.doe@gmail.com");

        // Mocking the profileService to throw an unexpected exception
        when(profileService.createStudent(studentDTO)).thenThrow(new RuntimeException("Unexpected exception"));

        // Act
        ResponseEntity<Response> responseEntity = profileController.createStudent(studentDTO);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getBody().getStatus());
        assertEquals("Internal server error", responseEntity.getBody().getError());

        // Verify that profileService.createStudent() was called
        verify(profileService, times(1)).createStudent(studentDTO);
    }

    @Test
    public void test_updateStudent_returns_ok() {
        // Arrange
        UUID studentId = UUID.randomUUID();
        StudentDTO studentDTO = new StudentDTO("John Doe", "john.doe@gmail.com");

        // Mocking the profileService to return the updated student
        when(profileService.updateStudent(studentId, studentDTO)).thenReturn(studentDTO);

        // Act
        ResponseEntity<Response> responseEntity = profileController.updateStudent(studentId, studentDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals(studentDTO, responseEntity.getBody().getData());

        // Verify that profileService.updateStudent() was called
        verify(profileService, times(1)).updateStudent(studentId, studentDTO);
    }

    @Test
    public void test_updateStudent_handles_not_found() {
        // Arrange
        UUID studentId = UUID.randomUUID();
        StudentDTO studentDTO = new StudentDTO("John Doe", "john.doe@gmail.com");

        // Mocking the profileService to throw ResourceNotFoundException
        when(profileService.updateStudent(studentId, studentDTO)).thenThrow(new ResourceNotFoundException("Student not found"));

        // Act
        ResponseEntity<Response> responseEntity = profileController.updateStudent(studentId, studentDTO);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getBody().getStatus());
        assertEquals("Student not found", responseEntity.getBody().getError());

        // Verify that profileService.updateStudent() was called
        verify(profileService, times(1)).updateStudent(studentId, studentDTO);
    }

    @Test
    public void test_updateStudent_handles_conflict() {
        // Arrange
        UUID studentId = UUID.randomUUID();
        StudentDTO studentDTO = new StudentDTO("John Doe", "john.doe@gmail.com");

        // Mocking the profileService to throw DataIntegrityViolationException
        when(profileService.updateStudent(studentId, studentDTO)).thenThrow(new DataIntegrityViolationException("Email already exists"));

        // Act
        ResponseEntity<Response> responseEntity = profileController.updateStudent(studentId, studentDTO);

        // Assert
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals(HttpStatus.CONFLICT.value(), responseEntity.getBody().getStatus());
        assertEquals("Student with this email already exists", responseEntity.getBody().getError());

        // Verify that profileService.updateStudent() was called
        verify(profileService, times(1)).updateStudent(studentId, studentDTO);
    }

    @Test
    public void test_updateStudent_handles_internal_server_error() {
        // Arrange
        UUID studentId = UUID.randomUUID();
        StudentDTO studentDTO = new StudentDTO("John Doe", "john.doe@gmail.com");

        // Mocking the profileService to throw an unexpected exception
        when(profileService.updateStudent(studentId, studentDTO)).thenThrow(new RuntimeException("Unexpected exception"));

        // Act
        ResponseEntity<Response> responseEntity = profileController.updateStudent(studentId, studentDTO);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getBody().getStatus());
        assertEquals("Internal server error", responseEntity.getBody().getError());

        // Verify that profileService.updateStudent() was called
        verify(profileService, times(1)).updateStudent(studentId, studentDTO);
    }


    @Test
    public void test_deleteStudent_returns_ok() {
        // Arrange
        UUID studentId = UUID.randomUUID();

        // Act
        ResponseEntity<Response> responseEntity = profileController.deleteStudent(studentId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals(null, responseEntity.getBody().getData());

        // Verify that profileService.deleteStudent() was called
        verify(profileService, times(1)).deleteStudent(studentId);
    }

    @Test
    public void test_deleteStudent_handles_not_found() {
        // Arrange
        UUID studentId = UUID.randomUUID();

        // Mocking the profileService to throw ResourceNotFoundException
        doThrow(new ResourceNotFoundException("Student not found")).when(profileService).deleteStudent(studentId);

        // Act
        ResponseEntity<Response> responseEntity = profileController.deleteStudent(studentId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getBody().getStatus());
        assertEquals("Student not found", responseEntity.getBody().getError());

        // Verify that profileService.deleteStudent() was called
        verify(profileService, times(1)).deleteStudent(studentId);
    }

    @Test
    public void test_deleteStudent_handles_internal_server_error() {
        // Arrange
        UUID studentId = UUID.randomUUID();

        // Mocking the profileService to throw an unexpected exception
        doThrow(new RuntimeException("Unexpected exception")).when(profileService).deleteStudent(studentId);

        // Act
        ResponseEntity<Response> responseEntity = profileController.deleteStudent(studentId);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getBody().getStatus());
        assertEquals("Internal server error", responseEntity.getBody().getError());

        // Verify that profileService.deleteStudent() was called
        verify(profileService, times(1)).deleteStudent(studentId);
    }

}