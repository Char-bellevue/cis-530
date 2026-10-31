/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week8.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import edu.bellevue.cis530.week8.entity.Student;
import edu.bellevue.cis530.week8.entity.StudentProfile;
import edu.bellevue.cis530.week8.service.StudentService;

/**
 * Web layer tests for the Student Services API.
 * Spring Security filters are disabled because authentication is not under test.
 */
@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc(addFilters = false)
class StudentControllerTest {

    // The id used by the sample student in these tests.
    private final Long STUDENT_ID = 1L;

    // The tool that sends fake HTTP requests to the controller.
    @Autowired
    private MockMvc gMockMvc;

    // The mocked service that replaces the real business logic.
    @MockitoBean
    private StudentService gStudentService;

    // The sample student that the mocked service returns.
    private Student gStudent;

    /**
     * Builds a fresh sample student with a valid profile before every test runs.
     * No parameters.
     * @return nothing; the gStudent field is ready for use by each test.
     */
    @BeforeEach
    void setUp() {
        StudentProfile lProfile = new StudentProfile();
        lProfile.setPhone("555-555-1234");
        lProfile.setAddress("100 Main Street");
        lProfile.setCity("Omaha");
        lProfile.setState("NE");
        lProfile.setZip("68101");
        lProfile.setEmergencyContactName("Charles Babbage");
        lProfile.setEmergencyContactPhone("555-555-9876");

        gStudent = new Student();
        gStudent.setId(STUDENT_ID);
        gStudent.setFirstName("Ada");
        gStudent.setLastName("Lovelace");
        gStudent.setEmail("ada.lovelace@example.com");
        gStudent.setMajor("Computer Science");
        gStudent.setGpa(new BigDecimal("3.85"));
        gStudent.setEnrollmentYear(2024);
        gStudent.setProfile(lProfile);
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
                    "firstName": "Ada",
                    "lastName": "Lovelace",
                    "email": "ada.lovelace@example.com",
                    "major": "Computer Science",
                    "gpa": 3.85,
                    "enrollmentYear": 2024,
                    "profile": {
                        "phone": "555-555-1234",
                        "address": "100 Main Street",
                        "city": "Omaha",
                        "state": "NE",
                        "zip": "68101",
                        "emergencyContactName": "Charles Babbage",
                        "emergencyContactPhone": "555-555-9876"
                    }
                }
                """; // Sample JSON input that passes every validation rule.
        when(gStudentService.createStudent(any(Student.class))).thenReturn(gStudent);

        gMockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(lRequestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Ada"))
                .andExpect(jsonPath("$.profile.city").value("Omaha"));

        verify(gStudentService).createStudent(any(Student.class)); // Confirm the service interaction.
    } // end createStudent

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
                .andExpect(jsonPath("$[0].firstName").value("Ada"));

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
                .andExpect(jsonPath("$.firstName").value("Ada"));

        verify(gStudentService).getStudentById(STUDENT_ID); // Confirm the service interaction.
    } // end getStudentById

    /**
     * Verifies that DELETE /api/students/{id} removes the student and returns 204 No Content.
     * No parameters.
     * @return nothing; the test passes when the status is correct and the service is called.
     * @throws Exception when MockMvc fails to perform the request.
     */
    @Test
    void deleteStudent() throws Exception {
        doNothing().when(gStudentService).deleteStudent(STUDENT_ID);

        gMockMvc.perform(delete("/api/students/{id}", STUDENT_ID))
                .andExpect(status().isNoContent());

        verify(gStudentService).deleteStudent(STUDENT_ID); // Confirm the service interaction.
    } // end deleteStudent
} // end StudentControllerTest
