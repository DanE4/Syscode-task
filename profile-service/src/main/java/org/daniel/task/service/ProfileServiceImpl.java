// ProfileServiceImpl.java
package org.daniel.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.daniel.task.dto.StudentDTO;
import org.daniel.task.entity.Student;
import org.daniel.task.exception.ResourceNotFoundException;
import org.daniel.task.mapper.StudentMapper;
import org.daniel.task.model.Response;
import org.daniel.task.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final StudentMapper studentMapper;

    private final ProfileRepository profileRepository;

    private final WebClient webClient;

    //Not tested, it's just for the sake of the task  (WebClient)
    public Mono<Response> getAddress() {
        // Use WebClient to make a GET request
        //ofc we could use .block() here but then we couldn't just utse .subscribe() or anything else on this returned Mono
        return webClient.get()
                .uri("/api/address/")
                .retrieve()
                .bodyToMono(Response.class)
                .doOnSubscribe(subscription -> log.info("Making GET request to /api/address"))
                .doOnSuccess(response -> log.info("Received response: {}", response))
                .doOnError(error -> log.error("Error making GET request: {}", error.getMessage()));
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> students = profileRepository.findAll();
        return students.stream()
                .map(studentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = studentMapper.mapToEntity(studentDTO);
        Student savedStudent = profileRepository.save(student);
        return studentMapper.mapToDto(savedStudent);
    }

    @Override
    public StudentDTO updateStudent(UUID id, StudentDTO studentDTO) {
        log.info("Updating student");
        profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        Student student = studentMapper.mapToEntity(studentDTO);
        student.setId(id);
        Student savedStudent = profileRepository.save(student);
        return studentMapper.mapToDto(savedStudent);
    }

    @Override
    public void deleteStudent(UUID id) {
        log.info("Deleting student");
        profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        profileRepository.deleteById(id);
    }

    //Not tested, it's just for the sake of the task
    @Override
    public Response getAddressFromAddressService() {
        Response response = getAddress().block();
        log.info("Response from address service: {}", response);
        return response;
    }
}
