/*
 Natarajan, C. (2026). CIS530-T301 Server-Side Development (2271-1). Bellevue University.
*/
package com.natarajan.week2.enrollment_api.controller;

import com.natarajan.week2.enrollment_api.model.Student;
import com.natarajan.week2.enrollment_api.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService gStudentService;

    /**
     * Creates the controller with its required service dependency.
     * @param studentService StudentService used for business logic.
     * @return No value; the constructor stores the service reference.
     */
    public StudentController(StudentService studentService) {
        this.gStudentService = studentService;
    } // end StudentController

    /**
     * Returns all students currently stored by the API.
     * @return ResponseEntity<?> containing the JSON list of all students.
     */
    @GetMapping
    public ResponseEntity<?> getStudents() {
        return ResponseEntity.ok(gStudentService.getAllStudents());
    } // end getStudents

    /**
     * Finds one student by identifier.
     * @param id Long student identifier to search for.
     * @return ResponseEntity<?> containing the student, or a 404 handled by GlobalExceptionHandler.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(gStudentService.getStudentById(id));
    } // end getStudentById

    /**
     * Adds a new student.
     * @param student Student object received in the request body.
     * @return ResponseEntity<?> containing the created student and HTTP 201 status.
     */
    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        Student lCreatedStudent = gStudentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(lCreatedStudent);
    } // end createStudent

    /**
     * Updates an existing student's data.
     * @param id Long student identifier to update.
     * @param student Student object containing replacement values.
     * @return ResponseEntity<?> containing the updated student, or a 404 handled by GlobalExceptionHandler.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        Student lUpdatedStudent = gStudentService.updateStudent(id, student);
        return ResponseEntity.ok(lUpdatedStudent);
    } // end updateStudent

    /**
     * Deletes a student by identifier.
     * @param id Long student identifier to delete.
     * @return ResponseEntity<?> containing a success message, or a 404 handled by GlobalExceptionHandler.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        gStudentService.deleteStudent(id);
        Map<String, String> lResponseBody = new LinkedHashMap<>();
        lResponseBody.put("message", "Student deleted successfully");
        lResponseBody.put("id", String.valueOf(id));
        return ResponseEntity.ok(lResponseBody);
    } // end deleteStudent
} // end StudentController
