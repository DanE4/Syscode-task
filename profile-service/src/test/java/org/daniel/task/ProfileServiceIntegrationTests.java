package org.daniel.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.daniel.task.dto.StudentDTO;
import org.daniel.task.mapper.StudentMapper;
import org.daniel.task.service.ProfileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // Use in-memory database for tests
@AutoConfigureMockMvc
class ProfileServiceIntegrationTests {


    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProfileServiceImpl profileService;

    StudentDTO createStudentDTO = new StudentDTO();

    @BeforeEach
    public void setup() {
        createStudentDTO.setName("John Doey");
        createStudentDTO.setEmail("john.doey@gmail.com");
    }

    @Test
    void contextLoads() {
        ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                .withUserConfiguration(ProfileServiceApp.class);

        contextRunner.run(context -> {
            assertThat(context).hasNotFailed();
        });
    }


    @Test
    public void testGetAllStudents() throws Exception {
        // Given
        List<StudentDTO> students = new ArrayList<>();
        StudentDTO student = new StudentDTO();
        student.setName("John Doe");
        student.setEmail("john.doe@example.com");
        students.add(student);

        StudentDTO student2 = new StudentDTO();
        student2.setName("Jane Smith");
        student2.setEmail("jane.smith@example.com");
        students.add(student2);

        // When & Then
        mockMvc.perform(get("/api/profile/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(200)))
                .andExpect(jsonPath("$.error", is(nullValue())))
                .andExpect(jsonPath("$.data", hasSize(3))) //we need to check for 3 because we have 2 students in
                // there + 1 is created in the other integration test
                .andExpect(jsonPath("$.data[0].name", is(students.get(0).getName())))
                .andExpect(jsonPath("$.data[0].email", is(students.get(0).getEmail())))
                .andExpect(jsonPath("$.data[1].name", is(students.get(1).getName())))
                .andExpect(jsonPath("$.data[1].email", is(students.get(1).getEmail())));
    }

    //Second Integration Test
    @Test
    public void testCreateStudent() throws Exception {
        // Given (already set up in @BeforeEach)
        ObjectMapper objectMapper = new ObjectMapper();

        // When & Then
        mockMvc.perform(post("/api/profile/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createStudentDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", is(HttpStatus.CREATED.value())))
                .andExpect(jsonPath("$.data.name", is(createStudentDTO.getName())))
                .andExpect(jsonPath("$.data.email", is(createStudentDTO.getEmail())))
                .andExpect(jsonPath("$.error", is(nullValue())));
    }

}
