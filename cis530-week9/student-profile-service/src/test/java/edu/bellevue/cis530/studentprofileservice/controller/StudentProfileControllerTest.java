/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.studentprofileservice.controller;

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

import edu.bellevue.cis530.studentprofileservice.entity.StudentProfile;
import edu.bellevue.cis530.studentprofileservice.exception.ResourceNotFoundException;
import edu.bellevue.cis530.studentprofileservice.service.StudentProfileService;

/**
 * Web layer tests for the Student Profile Service.
 * The service is mocked, so no database is used.
 */
@WebMvcTest(StudentProfileController.class)
class StudentProfileControllerTest {

    // The id used by the sample profile in these tests.
    private final Long PROFILE_ID = 1L;

    // The tool that sends fake HTTP requests to the controller.
    @Autowired
    private MockMvc gMockMvc;

    // The mocked service that replaces the real business logic.
    @MockitoBean
    private StudentProfileService gStudentProfileService;

    // The sample profile that the mocked service returns.
    private StudentProfile gProfile;

    /**
     * Builds a fresh sample profile before every test runs.
     * No parameters.
     * @return nothing; the gProfile field is ready for use by each test.
     */
    @BeforeEach
    void setUp() {
        gProfile = new StudentProfile(PROFILE_ID, 1L, "Computer Science", new BigDecimal("3.75"),
                "Enjoys backend development and databases.");
    } // end setUp

    /**
     * Verifies that POST /api/profiles creates a profile and returns it as JSON with a 201 status.
     * No parameters.
     * @return nothing; the test passes when the status and JSON fields match.
     * @throws Exception when MockMvc fails to perform the request.
     */
    @Test
    void createProfile() throws Exception {
        String lRequestJson = """
                {
                    "studentId": 1,
                    "major": "Computer Science",
                    "gpa": 3.75,
                    "bio": "Enjoys backend development and databases."
                }
                """; // Sample JSON input that passes every validation rule.
        when(gStudentProfileService.createProfile(any(StudentProfile.class))).thenReturn(gProfile);

        gMockMvc.perform(post("/api/profiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(lRequestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.major").value("Computer Science"));

        verify(gStudentProfileService).createProfile(any(StudentProfile.class)); // Confirm the service interaction.
    } // end createProfile

    /**
     * Verifies that POST /api/profiles returns 400 with a field error when the major is blank.
     * No parameters.
     * @return nothing; the test passes when the status is 400 and the major field error is reported.
     * @throws Exception when MockMvc fails to perform the request.
     */
    @Test
    void createProfileBlankMajor() throws Exception {
        String lRequestJson = """
                {
                    "studentId": 1,
                    "major": "",
                    "gpa": 3.75
                }
                """; // Sample JSON input that breaks the major rule.

        gMockMvc.perform(post("/api/profiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(lRequestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.major").isArray());
    } // end createProfileBlankMajor

    /**
     * Verifies that GET /api/profiles returns every profile as a JSON array with a 200 status.
     * No parameters.
     * @return nothing; the test passes when the status and first array element match.
     * @throws Exception when MockMvc fails to perform the request.
     */
    @Test
    void getAllProfiles() throws Exception {
        when(gStudentProfileService.getAllProfiles()).thenReturn(List.of(gProfile));

        gMockMvc.perform(get("/api/profiles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].major").value("Computer Science"));

        verify(gStudentProfileService).getAllProfiles(); // Confirm the service interaction.
    } // end getAllProfiles

    /**
     * Verifies that GET /api/profiles/{id} returns the matching profile as JSON with a 200 status.
     * No parameters.
     * @return nothing; the test passes when the status and JSON fields match.
     * @throws Exception when MockMvc fails to perform the request.
     */
    @Test
    void getProfileById() throws Exception {
        when(gStudentProfileService.getProfileById(PROFILE_ID)).thenReturn(gProfile);

        gMockMvc.perform(get("/api/profiles/{id}", PROFILE_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.gpa").value(3.75));

        verify(gStudentProfileService).getProfileById(PROFILE_ID); // Confirm the service interaction.
    } // end getProfileById

    /**
     * Verifies that GET /api/profiles/student/{studentId} returns the profile of that student with a 200 status.
     * No parameters.
     * @return nothing; the test passes when the status and student id match.
     * @throws Exception when MockMvc fails to perform the request.
     */
    @Test
    void getProfileByStudentId() throws Exception {
        when(gStudentProfileService.getProfileByStudentId(1L)).thenReturn(gProfile);

        gMockMvc.perform(get("/api/profiles/student/{studentId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentId").value(1));

        verify(gStudentProfileService).getProfileByStudentId(1L); // Confirm the service interaction.
    } // end getProfileByStudentId

    /**
     * Verifies that GET /api/profiles/student/{studentId} returns 404 with an error body when no profile exists.
     * No parameters.
     * @return nothing; the test passes when the status is 404 and the message matches.
     * @throws Exception when MockMvc fails to perform the request.
     */
    @Test
    void getProfileByStudentIdNotFound() throws Exception {
        when(gStudentProfileService.getProfileByStudentId(99L))
                .thenThrow(new ResourceNotFoundException("Profile not found for student id: 99"));

        gMockMvc.perform(get("/api/profiles/student/{studentId}", 99L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Profile not found for student id: 99"));
    } // end getProfileByStudentIdNotFound

    /**
     * Verifies that DELETE /api/profiles/{id} removes the profile and returns 200 with a confirmation message.
     * No parameters.
     * @return nothing; the test passes when the status and message are correct and the service is called.
     * @throws Exception when MockMvc fails to perform the request.
     */
    @Test
    void deleteProfile() throws Exception {
        doNothing().when(gStudentProfileService).deleteProfile(PROFILE_ID);

        gMockMvc.perform(delete("/api/profiles/{id}", PROFILE_ID))
                .andExpect(status().isOk())
                .andExpect(content().string("Profile deleted successfully"));

        verify(gStudentProfileService).deleteProfile(PROFILE_ID); // Confirm the service interaction.
    } // end deleteProfile
} // end StudentProfileControllerTest
