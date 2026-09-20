package com.natarajan.week2.enrollment_api.controller;

import com.natarajan.week2.enrollment_api.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StudentController {

    private final List<Student> students = new ArrayList<>();

    public StudentController() {
        students.add(new Student("S001", "John", "Doe", "john.doe@example.com"));
        students.add(new Student("S002", "Jane", "Smith", "jane.smith@example.com"));
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        return students;
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable String id) {
        for (Student student : students) {
            if (student.getStudentId().equalsIgnoreCase(id)) {
                return ResponseEntity.ok(student);
            }
        }

        return createNotFoundResponse(id);
    }

    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        students.add(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Object> updateStudent(@PathVariable String id, @RequestBody Student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            Student currentStudent = students.get(i);
            if (currentStudent.getStudentId().equalsIgnoreCase(id)) {
                currentStudent.setFirstName(updatedStudent.getFirstName());
                currentStudent.setLastName(updatedStudent.getLastName());
                currentStudent.setEmail(updatedStudent.getEmail());
                currentStudent.setStudentId(id);
                return ResponseEntity.ok(currentStudent);
            }
        }

        return createNotFoundResponse(id);
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable String id) {
        for (Student student : students) {
            if (student.getStudentId().equalsIgnoreCase(id)) {
                students.remove(student);
                Map<String, String> response = new LinkedHashMap<>();
                response.put("message", "Student deleted successfully");
                response.put("studentId", id);
                return ResponseEntity.ok(response);
            }
        }

        return createNotFoundResponse(id);
    }

    private ResponseEntity<Object> createNotFoundResponse(String id) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("status", HttpStatus.NOT_FOUND.value());
        error.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        error.put("message", "Student not found with id: " + id);
        error.put("path", "/api/students/" + id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
