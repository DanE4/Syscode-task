package org.daniel.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.daniel.task.dto.StudentDTO;
import org.daniel.task.exception.ResourceNotFoundException;
import org.daniel.task.model.Response;
import org.daniel.task.service.ProfileServiceImpl;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileController {
    private final ProfileServiceImpl profileService;


    @Operation(summary = "Get all students", description = "Fetch all students", tags = {"Profile"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content(
                                    mediaType = "application/json",
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation =
                                            Response.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Internal Server Error",
                            responseCode = "500"
                    )
            }
    )
    @GetMapping("/")
    public ResponseEntity<Response> getAllStudents() {
        try {
            return ResponseEntity.ok(Response.builder()
                    .status(HttpStatus.OK.value())
                    .data(profileService.getAllStudents())
                    .build());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .error("Internal server error")
                    .build());
        }
    }

    @Operation(summary = "Create a student", description = "Create a student", tags = {"Profile"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "201",
                            content = @io.swagger.v3.oas.annotations.media.Content(
                                    mediaType = "application/json",
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation =
                                            Response.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Conflict",
                            responseCode = "409"
                    ),
                    @ApiResponse(
                            description = "Internal Server Error",
                            responseCode = "500"
                    )
            }
    )
    @PostMapping("/")
    public ResponseEntity<Response> createStudent(@RequestBody @Valid StudentDTO studentDTO) {
        try {
            return ResponseEntity.created(null).body(Response.builder()
                    .status(HttpStatus.CREATED.value())
                    .data(profileService.createStudent(studentDTO))
                    .build());
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Response.builder()
                    .status(HttpStatus.CONFLICT.value())
                    .error("Student with this email already exists")
                    .build());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .error("Internal server error")
                    .build());
        }
    }

    @Operation(summary = "Update a student", description = "Update a student", tags = {"Profile"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content(
                                    mediaType = "application/json",
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation =
                                            Response.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Conflict",
                            responseCode = "409"
                    ),
                    @ApiResponse(
                            description = "Internal Server Error",
                            responseCode = "500"
                    )
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<Response> updateStudent(@PathVariable UUID id, @RequestBody @Valid StudentDTO studentDTO) {
        try {
            return ResponseEntity.ok(Response.builder()
                    .status(HttpStatus.OK.value())
                    .data(profileService.updateStudent(id, studentDTO))
                    .build());
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .error("Student not found")
                    .build());
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Response.builder()
                    .status(HttpStatus.CONFLICT.value())
                    .error("Student with this email already exists")
                    .build());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .error("Internal server error")
                    .build());
        }
    }

    @Operation(summary = "Delete a student", description = "Delete a student", tags = {"Profile"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content(
                                    mediaType = "application/json",
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation =
                                            Response.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Internal Server Error",
                            responseCode = "500"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteStudent(@PathVariable UUID id) {
        try {
            profileService.deleteStudent(id);
            return ResponseEntity.ok(Response.builder()
                    .status(HttpStatus.OK.value())
                    .data(null)
                    .build());
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .error("Student not found")
                    .build());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .error("Internal server error")
                    .build());
        }
    }

    @Operation(summary = "Get a random address from the other service", description = "Get a random address from the " +
            "other service", tags = {"Profile"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content(
                                    mediaType = "application/json",
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation =
                                            Response.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Internal Server Error",
                            responseCode = "500"
                    )
            }
    )
    //!This has made so you can try it out easily without needing to implement WebClient anywhere in the code :)
    @GetMapping("/address")
    public ResponseEntity<Response> getAddressFromAddressService() {
        try {
            return ResponseEntity.ok(profileService.getAddressFromAddressService());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .error("Internal server error")
                    .build());
        }
    }
}
