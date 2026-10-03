/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week4.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.bellevue.cis530.week4.entity.Student;
import edu.bellevue.cis530.week4.service.StudentService;

/**
 * REST controller that exposes the student endpoints under /api/students.
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {

    // Service that holds the business logic for student records.
    private final StudentService gStudentService;

    /**
     * Creates the controller and receives the service through constructor injection.
     * @param studentService StudentService the service that Spring provides
     * @return none, a new controller object is created.
     */
    public StudentController(StudentService studentService) {
        this.gStudentService = studentService; // Keep the injected service.
    } // end StudentController

    /**
     * Handles POST /api/students and creates a new student.
     * @param student Student the student data from the JSON request body
     * @return ResponseEntity<Student> the saved student with HTTP status 201.
     */
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student lCreatedStudent = gStudentService.createStudent(student);
        return new ResponseEntity<>(lCreatedStudent, HttpStatus.CREATED);
    } // end createStudent

    /**
     * Handles GET /api/students and returns every student.
     * @return ResponseEntity<List<Student>> all students with HTTP status 200.
     */
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(gStudentService.getAllStudents());
    } // end getAllStudents

    /**
     * Handles GET /api/students/{id} and returns one student.
     * @param id Long the ID taken from the URL
     * @return ResponseEntity<Student> the matching student with HTTP status 200.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(gStudentService.getStudentById(id));
    } // end getStudentById

    /**
     * Handles PUT /api/students/{id} and updates a student.
     * @param id Long the ID taken from the URL
     * @param student Student the new student data from the JSON request body
     * @return ResponseEntity<Student> the updated student with HTTP status 200.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") Long id, @RequestBody Student student) {
        return ResponseEntity.ok(gStudentService.updateStudent(id, student));
    } // end updateStudent

    /**
     * Handles DELETE /api/students/{id} and deletes a student.
     * @param id Long the ID taken from the URL
     * @return ResponseEntity<Map<String, Object>> a JSON confirmation message with HTTP status 200.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteStudentById(@PathVariable("id") Long id) {
        gStudentService.deleteStudentById(id);
        Map<String, Object> lBody = new LinkedHashMap<>(); // Keeps the fields in insertion order.
        lBody.put("message", "Student deleted successfully.");
        lBody.put("id", id);
        return ResponseEntity.ok(lBody);
    } // end deleteStudentById

    /**
     * Handles GET /api/students/major/{major} and finds students by major.
     * @param major String the major taken from the URL
     * @return ResponseEntity<List<Student>> the matching students with HTTP status 200.
     */
    @GetMapping("/major/{major}")
    public ResponseEntity<List<Student>> getByMajor(@PathVariable("major") String major) {
        return ResponseEntity.ok(gStudentService.getByMajor(major));
    } // end getByMajor

    /**
     * Handles GET /api/students/gpa/{gpa} and finds students with a higher GPA.
     * @param gpa Double the GPA taken from the URL
     * @return ResponseEntity<List<Student>> the matching students with HTTP status 200.
     */
    @GetMapping("/gpa/{gpa}")
    public ResponseEntity<List<Student>> getByGpaGreaterThan(@PathVariable("gpa") Double gpa) {
        return ResponseEntity.ok(gStudentService.getByGpaGreaterThan(gpa));
    } // end getByGpaGreaterThan

    /**
     * Handles GET /api/students/year/{year} and finds students enrolled after a year.
     * @param year Integer the year taken from the URL
     * @return ResponseEntity<List<Student>> the matching students with HTTP status 200.
     */
    @GetMapping("/year/{year}")
    public ResponseEntity<List<Student>> getByEnrollmentYearGreaterThan(@PathVariable("year") Integer year) {
        return ResponseEntity.ok(gStudentService.getByEnrollmentYearGreaterThan(year));
    } // end getByEnrollmentYearGreaterThan

    /**
     * Handles GET /api/students/top3 and returns the three students with the highest GPA.
     * @return ResponseEntity<List<Student>> the top three students with HTTP status 200.
     */
    @GetMapping("/top3")
    public ResponseEntity<List<Student>> getTop3ByGpa() {
        return ResponseEntity.ok(gStudentService.getTop3ByGpa());
    } // end getTop3ByGpa

    /**
     * Handles GET /api/students/sort and returns students sorted by last name.
     * @param direction String either asc or desc, which defaults to asc
     * @return ResponseEntity<List<Student>> the sorted students with HTTP status 200.
     */
    @GetMapping("/sort")
    public ResponseEntity<List<Student>> getAllSortedByLastName(
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {
        return ResponseEntity.ok(gStudentService.getAllSortedByLastName(direction));
    } // end getAllSortedByLastName

    /**
     * Handles GET /api/students/page and returns one page of students.
     * @param page int the zero-based page number, which defaults to 0
     * @param size int the number of students per page, which defaults to 5
     * @return ResponseEntity<Page<Student>> the requested page with HTTP status 200.
     */
    @GetMapping("/page")
    public ResponseEntity<Page<Student>> getStudentsPaged(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {
        return ResponseEntity.ok(gStudentService.getStudentsPaged(page, size));
    } // end getStudentsPaged

    /**
     * Handles GET /api/students/major-jpql/{major} and finds students by major with JPQL.
     * @param major String the major taken from the URL
     * @return ResponseEntity<List<Student>> the matching students with HTTP status 200.
     */
    @GetMapping("/major-jpql/{major}")
    public ResponseEntity<List<Student>> getByMajorJpql(@PathVariable("major") String major) {
        return ResponseEntity.ok(gStudentService.getByMajorJpql(major));
    } // end getByMajorJpql

    /**
     * Handles DELETE /api/students/year/{year}/jpql and deletes students by enrollment year with JPQL.
     * @param year Integer the enrollment year taken from the URL
     * @return ResponseEntity<Map<String, Object>> a JSON message with the delete count and HTTP status 200.
     */
    @DeleteMapping("/year/{year}/jpql")
    public ResponseEntity<Map<String, Object>> deleteByEnrollmentYearJpql(@PathVariable("year") Integer year) {
        int lDeletedCount = gStudentService.deleteByEnrollmentYearJpql(year);
        Map<String, Object> lBody = new LinkedHashMap<>(); // Keeps the fields in insertion order.
        lBody.put("message", "Students enrolled in " + year + " were deleted.");
        lBody.put("deletedCount", lDeletedCount);
        return ResponseEntity.ok(lBody);
    } // end deleteByEnrollmentYearJpql
} // end StudentController
