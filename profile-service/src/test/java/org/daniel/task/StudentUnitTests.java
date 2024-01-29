package org.daniel.task;

import org.daniel.task.entity.Student;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class StudentUnitTests {


    // Can create a new Student object with valid name and email
    @Test
    public void test_create_student_with_valid_name_and_email() {
        // Arrange
        UUID id = UUID.randomUUID();
        String name = "John Doe";
        String email = "john.doe@gmail.com";

        // Act
        Student student = new Student(id, name, email);

        // Assert
        assertNotNull(student);
        assertEquals(id, student.getId());
        assertEquals(name, student.getName());
        assertEquals(email, student.getEmail());
    }

    // Can update an existing Student object with valid name and email
    @Test
    public void test_update_student_with_valid_name_and_email() {
        // Arrange
        UUID id = UUID.randomUUID();
        String name = "John Doe";
        String email = "john.doe@gmail.com";
        Student student = new Student(id, name, email);

        String updatedName = "Jane Smith";
        String updatedEmail = "jane.smith@gmail.com";

        // Act
        student.setName(updatedName);
        student.setEmail(updatedEmail);

        // Assert
        assertEquals(updatedName, student.getName());
        assertEquals(updatedEmail, student.getEmail());
    }

    // Can retrieve the ID of a Student object
    @Test
    public void test_retrieve_student_id() {
        // Arrange
        UUID id = UUID.randomUUID();
        String name = "John Doe";
        String email = "john.doe@gmail.com";
        Student student = new Student(id, name, email);

        // Act
        UUID retrievedId = student.getId();

        // Assert
        assertEquals(id, retrievedId);
    }

    // Cannot create a new Student object with a null name
    @Test
    public void test_cannot_create_student_with_null_name() {
        // Arrange
        UUID id = UUID.randomUUID();
        String nullName = null;
        String email = "john.doe@gmail.com";

        // Act and Assert
        assertThrows(NullPointerException.class, () -> {
            new Student(id, nullName, email);
        });
    }

    // Cannot create a new Student object with a null email
    @Test
    public void test_cannot_create_student_with_null_email() {
        // Arrange
        UUID id = UUID.randomUUID();
        String name = "John Doe";
        String nullEmail = null;

        // Act and Assert
        assertThrows(NullPointerException.class, () -> {
            new Student(id, name, nullEmail);
        });
    }
}
