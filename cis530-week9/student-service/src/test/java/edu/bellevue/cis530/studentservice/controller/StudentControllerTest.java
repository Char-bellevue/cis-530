/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.studentservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import edu.bellevue.cis530.studentservice.client.StudentProfileClient;
import edu.bellevue.cis530.studentservice.dto.StudentProfileDto;
import edu.bellevue.cis530.studentservice.entity.Student;
import edu.bellevue.cis530.studentservice.exception.ResourceNotFoundException;
import edu.bellevue.cis530.studentservice.service.StudentService;

/**
 * Web layer tests for the Student Service.
 * The service and the Feign client are mocked, so no database or other microservice is used.
 */
@WebMvcTest(StudentController.class)
class StudentControllerTest {

    // The id used by the sample student in these tests.
    private final Long STUDENT_ID = 1L;

    // The tool that sends fake HTTP requests to the controller.
    @Autowired
    private MockMvc gMockMvc;

    // The mocked service that replaces the real business logic.
    @MockitoBean
    private StudentService gStudentService;

    // The mocked Feign client, so the Student Profile Service is never called.
    @MockitoBean
    private StudentProfileClient gStudentProfileClient;

    // The sample student that the mocked service returns.
    private Student gStudent;

    /**
     * Builds a fresh sample student before every test runs.
     * No parameters.
     * @return nothing; the gStudent field is ready for use by each test.
     */
    @BeforeEach
    void setUp() {
        gStudent = new Student(STUDENT_ID, "Ali", "Rezaei", "ali.rezaei@example.com");
    } // end setUp

    /**
     * Verifies that POST /api/students creates a student and returns it as JSON with a 201 status.
     * No parameters.
     * @return nothing; the test passes when the status and JSON fields match.
     * @throws Exception when MockMvc fails to perform the request.
     */
    @Test
    void createStudent() throws Exception {
        String lRequestJson = """
                {
                    "firstName": "Ali",
                    "lastName": "Rezaei",
                    "email": "ali.rezaei@example.com"
                }
                """; // Sample JSON input that passes every validation rule.
        when(gStudentService.createStudent(any(Student.class))).thenReturn(gStudent);

        gMockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(lRequestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Ali"));

        verify(gStudentService).createStudent(any(Student.class)); // Confirm the service interaction.
    } // end createStudent

    /**
     * Verifies that POST /api/students returns 400 with a field error when the email is not valid.
     * No parameters.
     * @return nothing; the test passes when the status is 400 and the email field error is reported.
     * @throws Exception when MockMvc fails to perform the request.
     */
    @Test
    void createStudentInvalidEmail() throws Exception {
        String lRequestJson = """
                {
                    "firstName": "Ali",
                    "lastName": "Rezaei",
                    "email": "not-an-email"
                }
                """; // Sample JSON input that breaks the email rule.

        gMockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(lRequestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.email").isArray());
    } // end createStudentInvalidEmail

    /**
     * Verifies that GET /api/students returns every student as a JSON array with a 200 status.
     * No parameters.
     * @return nothing; the test passes when the status and first array element match.
     * @throws Exception when MockMvc fails to perform the request.
     */
    @Test
    void getAllStudents() throws Exception {
        when(gStudentService.getAllStudents()).thenReturn(List.of(gStudent));

        gMockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Ali"));

        verify(gStudentService).getAllStudents(); // Confirm the service interaction.
    } // end getAllStudents

    /**
     * Verifies that GET /api/students/{id} returns the matching student as JSON with a 200 status.
     * No parameters.
     * @return nothing; the test passes when the status and JSON fields match.
     * @throws Exception when MockMvc fails to perform the request.
     */
    @Test
    void getStudentById() throws Exception {
        when(gStudentService.getStudentById(STUDENT_ID)).thenReturn(gStudent);

        gMockMvc.perform(get("/api/students/{id}", STUDENT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.lastName").value("Rezaei"));

        verify(gStudentService).getStudentById(STUDENT_ID); // Confirm the service interaction.
    } // end getStudentById

    /**
     * Verifies that GET /api/students/{id} returns 404 with an error body when the student does not exist.
     * No parameters.
     * @return nothing; the test passes when the status is 404 and the message matches.
     * @throws Exception when MockMvc fails to perform the request.
     */
    @Test
    void getStudentByIdNotFound() throws Exception {
        when(gStudentService.getStudentById(99L))
                .thenThrow(new ResourceNotFoundException("Student not found with id: 99"));

        gMockMvc.perform(get("/api/students/{id}", 99L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Student not found with id: 99"));
    } // end getStudentByIdNotFound

    /**
     * Verifies that DELETE /api/students/{id} removes the student and returns 200 with a confirmation message.
     * No parameters.
     * @return nothing; the test passes when the status and message are correct and the service is called.
     * @throws Exception when MockMvc fails to perform the request.
     */
    @Test
    void deleteStudent() throws Exception {
        doNothing().when(gStudentService).deleteStudent(STUDENT_ID);

        gMockMvc.perform(delete("/api/students/{id}", STUDENT_ID))
                .andExpect(status().isOk())
                .andExpect(content().string("Student deleted successfully"));

        verify(gStudentService).deleteStudent(STUDENT_ID); // Confirm the service interaction.
    } // end deleteStudent

    /**
     * Verifies that GET /api/students/{id}/full returns the student combined with its profile.
     * No parameters.
     * @return nothing; the test passes when the status and both nested JSON objects match.
     * @throws Exception when MockMvc fails to perform the request.
     */
    @Test
    void getStudentWithProfile() throws Exception {
        StudentProfileDto lProfile = new StudentProfileDto();
        lProfile.setId(1L);
        lProfile.setStudentId(STUDENT_ID);
        lProfile.setMajor("Computer Science");
        lProfile.setGpa(new BigDecimal("3.75"));
        lProfile.setBio("Enjoys backend development and databases.");
        when(gStudentService.getStudentById(STUDENT_ID)).thenReturn(gStudent);
        when(gStudentProfileClient.getProfileByStudentId(STUDENT_ID)).thenReturn(lProfile);

        gMockMvc.perform(get("/api/students/{id}/full", STUDENT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.student.firstName").value("Ali"))
                .andExpect(jsonPath("$.profile.major").value("Computer Science"));

        verify(gStudentProfileClient).getProfileByStudentId(STUDENT_ID); // Confirm the Feign interaction.
    } // end getStudentWithProfile
} // end StudentControllerTest
