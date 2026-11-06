/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.studentservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.bellevue.cis530.studentservice.client.StudentProfileClient;
import edu.bellevue.cis530.studentservice.dto.StudentProfileDto;
import edu.bellevue.cis530.studentservice.dto.StudentWithProfileResponse;
import edu.bellevue.cis530.studentservice.entity.Student;
import edu.bellevue.cis530.studentservice.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * REST controller that exposes the student endpoints under /api/students.
 */
@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    // The service that holds the business logic for students.
    private final StudentService gStudentService;

    // The Feign client used to get profile data from the Student Profile Service.
    private final StudentProfileClient gStudentProfileClient;

    /**
     * Creates a student from a JSON body.
     *
     * @param student Student the request body, validated with @Valid.
     * @return ResponseEntity&lt;Student&gt; a 201 Created response with the saved student.
     */
    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
        Student lCreatedStudent = gStudentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(lCreatedStudent);
    } // end createStudent

    /**
     * Gets all students.
     *
     * @return ResponseEntity&lt;List&lt;Student&gt;&gt; a 200 OK response with every student.
     */
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(gStudentService.getAllStudents());
    } // end getAllStudents

    /**
     * Gets one student by id.
     *
     * @param id Long the id of the student to find.
     * @return ResponseEntity&lt;Student&gt; a 200 OK response with the matching student.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(gStudentService.getStudentById(id));
    } // end getStudentById

    /**
     * Deletes one student by id.
     *
     * @param id Long the id of the student to delete.
     * @return ResponseEntity&lt;String&gt; a 200 OK response with a confirmation message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        gStudentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully");
    } // end deleteStudent

    /**
     * Gets one student together with the matching profile from the Student Profile Service.
     *
     * @param id Long the id of the student to find.
     * @return ResponseEntity&lt;StudentWithProfileResponse&gt; a 200 OK response with the student and profile.
     */
    @GetMapping("/{id}/full")
    public ResponseEntity<StudentWithProfileResponse> getStudentWithProfile(@PathVariable Long id) {
        Student lStudent = gStudentService.getStudentById(id); // Read the student from student_db.
        StudentProfileDto lProfile = gStudentProfileClient.getProfileByStudentId(id); // Call the other service.
        return ResponseEntity.ok(new StudentWithProfileResponse(lStudent, lProfile));
    } // end getStudentWithProfile
} // end StudentController
