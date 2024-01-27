package org.daniel.task.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class StudentController {
    //private final StudentService;
    //listing
    @GetMapping("/")
    public ResponseEntity<String> getAllStudents() {
        return ResponseEntity.ok("");
    }

    @PostMapping("/")
    public ResponseEntity<String> createStudent() {
        return ResponseEntity.ok("");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable String id) {
        return ResponseEntity.ok("");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable String id) {
        return ResponseEntity.ok("");
    }
}
