/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week7;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * REST controller that exposes the student and profile endpoints under /api/students.
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {

    // The service that holds the business logic for students.
    private final StudentService gStudentService;

    /**
     * Creates the controller with the service that Spring injects.
     *
     * @param studentService StudentService the service used to handle student requests.
     * @return nothing; a new StudentController object is constructed.
     */
    public StudentController(StudentService studentService) {
        this.gStudentService = studentService; // Keep the injected service for later use.
    } // end StudentController

    /**
     * Creates a student and its profile from a single JSON body.
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
     * @param id Long the id taken from the URL path.
     * @return ResponseEntity&lt;Student&gt; a 200 OK response with the student.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(gStudentService.getStudentById(id));
    } // end getStudentById

    /**
     * Updates a student and its profile from a single JSON body.
     *
     * @param id Long the id taken from the URL path.
     * @param student Student the request body, validated with @Valid.
     * @return ResponseEntity&lt;Student&gt; a 200 OK response with the updated student.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") Long id, @Valid @RequestBody Student student) {
        Student lUpdatedStudent = gStudentService.updateStudent(id, student);
        return ResponseEntity.ok(lUpdatedStudent);
    } // end updateStudent

    /**
     * Deletes a student and its profile.
     *
     * @param id Long the id taken from the URL path.
     * @return ResponseEntity&lt;Void&gt; a 204 No Content response with an empty body.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") Long id) {
        gStudentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    } // end deleteStudent

    /**
     * Gets the profile that belongs to one student.
     *
     * @param id Long the id of the student taken from the URL path.
     * @return ResponseEntity&lt;StudentProfile&gt; a 200 OK response with the profile.
     */
    @GetMapping("/{id}/profile")
    public ResponseEntity<StudentProfile> getProfileByStudentId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(gStudentService.getProfileByStudentId(id));
    } // end getProfileByStudentId

    /**
     * Finds students by major.
     *
     * @param major String the major taken from the URL path.
     * @return ResponseEntity&lt;List&lt;Student&gt;&gt; a 200 OK response with the matching students.
     */
    @GetMapping("/major/{major}")
    public ResponseEntity<List<Student>> getStudentsByMajor(@PathVariable("major") String major) {
        return ResponseEntity.ok(gStudentService.getStudentsByMajor(major));
    } // end getStudentsByMajor

    /**
     * Finds students by enrollment year.
     *
     * @param year Integer the enrollment year taken from the URL path.
     * @return ResponseEntity&lt;List&lt;Student&gt;&gt; a 200 OK response with the matching students.
     */
    @GetMapping("/year/{year}")
    public ResponseEntity<List<Student>> getStudentsByEnrollmentYear(@PathVariable("year") Integer year) {
        return ResponseEntity.ok(gStudentService.getStudentsByEnrollmentYear(year));
    } // end getStudentsByEnrollmentYear
} // end StudentController
