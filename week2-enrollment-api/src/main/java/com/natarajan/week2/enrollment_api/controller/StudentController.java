/*
 Natarajan, C. (2026). CIS530-T301 Server-Side Development (2271-1). Bellevue University.
*/
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

    private final List<Student> gStudents = new ArrayList<>();

    /**
     * Creates the controller with two sample students.
     * @return No value; the constructor initializes the in-memory student list.
     */
    public StudentController() {
        gStudents.add(new Student("S001", "John", "Doe", "john.doe@example.com"));
        gStudents.add(new Student("S002", "Jane", "Smith", "jane.smith@example.com"));
    } // end StudentController

    /**
     * Returns all students currently stored by the API.
     * @return List<Student> containing all students.
     */
    @GetMapping("/students")
    public List<Student> getStudents() {
        return gStudents;
    } // end getStudents

    /**
     * Finds one student by identifier.
     * @param id String student identifier to search for.
     * @return ResponseEntity<?> containing the student or a JSON not-found response.
     */
    @GetMapping("/students/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable String id) {
        for (Student lStudent : gStudents) {
            if (lStudent.getStudentId().equalsIgnoreCase(id)) {
                return ResponseEntity.ok(lStudent);
            }
        }

        return createNotFoundResponse(id);
    } // end getStudentById

    /**
     * Adds a new student to the in-memory student list.
     * @param student Student object received in the request body.
     * @return ResponseEntity<Student> containing the created student and HTTP 201 status.
     */
    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        gStudents.add(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(student);
    } // end createStudent

    /**
     * Updates an existing student's non-identifier fields.
     * @param id String student identifier to update.
     * @param updatedStudent Student object containing replacement values.
     * @return ResponseEntity<Object> containing the updated student or a JSON not-found response.
     */
    @PutMapping("/students/{id}")
    public ResponseEntity<Object> updateStudent(@PathVariable String id, @RequestBody Student updatedStudent) {
        for (Student lCurrentStudent : gStudents) {
            if (lCurrentStudent.getStudentId().equalsIgnoreCase(id)) {
                lCurrentStudent.setFirstName(updatedStudent.getFirstName());
                lCurrentStudent.setLastName(updatedStudent.getLastName());
                lCurrentStudent.setEmail(updatedStudent.getEmail());
                lCurrentStudent.setStudentId(id);
                return ResponseEntity.ok(lCurrentStudent);
            }
        }

        return createNotFoundResponse(id);
    } // end updateStudent

    /**
     * Deletes a student by identifier.
     * @param id String student identifier to delete.
     * @return ResponseEntity<Object> containing a success message or a JSON not-found response.
     */
    @DeleteMapping("/students/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable String id) {
        for (Student lStudent : gStudents) {
            if (lStudent.getStudentId().equalsIgnoreCase(id)) {
                gStudents.remove(lStudent);
                Map<String, String> response = new LinkedHashMap<>();
                response.put("message", "Student deleted successfully");
                response.put("studentId", id);
                return ResponseEntity.ok(response);
            }
        }

        return createNotFoundResponse(id);
    } // end deleteStudent

    /**
     * Creates a consistent JSON response for a missing student.
     * @param id String student identifier that was not found.
     * @return ResponseEntity<Object> containing the HTTP 404 error details.
     */
    private ResponseEntity<Object> createNotFoundResponse(String id) {
        Map<String, Object> lError = new LinkedHashMap<>();
        lError.put("status", HttpStatus.NOT_FOUND.value());
        lError.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        lError.put("message", "Student not found with id: " + id);
        lError.put("path", "/api/students/" + id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(lError);
    } // end createNotFoundResponse
} // end StudentController
