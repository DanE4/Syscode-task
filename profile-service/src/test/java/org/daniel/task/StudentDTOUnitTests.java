package org.daniel.task;

import org.daniel.task.dto.StudentDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StudentDTOUnitTests {


    // Should create a new instance of StudentDTO with valid name and email
    @Test
    public void test_create_instance_with_valid_name_and_email() {
        // Arrange
        String name = "Jane Doe";
        String email = "jane.doe@gmail.com";

        // Act
        StudentDTO studentDTO = new StudentDTO(name, email);

        // Assert
        assertNotNull(studentDTO);
        assertEquals(name, studentDTO.getName());
        assertEquals(email, studentDTO.getEmail());
    }

    // Should be able to get the name of a StudentDTO instance
    @Test
    public void test_get_name() {
        // Arrange
        String name = "Jane Doe";
        String email = "jane.doe@gmail.com";
        StudentDTO studentDTO = new StudentDTO(name, email);

        // Act
        String actualName = studentDTO.getName();

        // Assert
        assertEquals(name, actualName);
    }

    // Should be able to get the email of a StudentDTO instance
    @Test
    public void test_get_email() {
        // Arrange
        String name = "Jane Doe";
        String email = "jane.doe@gmail.com";
        StudentDTO studentDTO = new StudentDTO(name, email);

        // Act
        String actualEmail = studentDTO.getEmail();

        // Assert
        assertEquals(email, actualEmail);
    }

    // Should be able to create a new instance of StudentDTO with an empty name
    @Test
    public void test_create_instance_with_empty_name() {
        // Arrange
        String name = "";
        String email = "jane.doe@gmail.com";

        // Act
        StudentDTO studentDTO = new StudentDTO(name, email);

        // Assert
        assertNotNull(studentDTO);
        assertEquals(name, studentDTO.getName());
        assertEquals(email, studentDTO.getEmail());
    }

    // Should be able to create a new instance of StudentDTO with an empty email
    @Test
    public void test_create_instance_with_empty_email() {
        // Arrange
        String name = "Jane Doe";
        String email = "";

        // Act
        StudentDTO studentDTO = new StudentDTO(name, email);

        // Assert
        assertNotNull(studentDTO);
        assertEquals(name, studentDTO.getName());
        assertEquals(email, studentDTO.getEmail());
    }
}