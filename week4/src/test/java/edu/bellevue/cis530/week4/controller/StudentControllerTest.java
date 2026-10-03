/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week4.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import edu.bellevue.cis530.week4.entity.Student;
import edu.bellevue.cis530.week4.exception.GlobalExceptionHandler;
import edu.bellevue.cis530.week4.exception.ResourceNotFoundException;
import edu.bellevue.cis530.week4.service.StudentService;

/**
 * Test class for StudentController that uses MockMvc and a mocked service, so no database is needed.
 */
@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    // Mocked service that replaces the real business logic.
    @Mock
    private StudentService gStudentService;

    // MockMvc object that sends fake HTTP requests to the controller.
    private MockMvc gMockMvc;

    /**
     * Builds the MockMvc object with the controller and the global exception handler before each test.
     * @return none, gMockMvc is ready to use when this method ends.
     */
    @BeforeEach
    void setUp() {
        StudentController lController = new StudentController(gStudentService);
        gMockMvc = MockMvcBuilders.standaloneSetup(lController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    } // end setUp

    /**
     * Builds a valid student with ID 1 that the tests can use.
     * @return Student a student with every field filled in.
     */
    private Student buildStudent() {
        Student lStudent = new Student();
        lStudent.setId(1L);
        lStudent.setFirstName("Sara");
        lStudent.setLastName("Feilmann");
        lStudent.setEmail("sara.feilmann@example.com");
        lStudent.setMajor("Computer Science");
        lStudent.setGpa(3.75);
        lStudent.setEnrollmentYear(2024);
        return lStudent;
    } // end buildStudent

    /**
     * Builds the sample JSON request body from the assignment.
     * @return String the JSON text for a new student.
     */
    private String buildStudentJson() {
        return """
                {
                  "firstName": "Sara",
                  "lastName": "Feilmann",
                  "email": "sara.feilmann@example.com",
                  "major": "Computer Science",
                  "gpa": 3.75,
                  "enrollmentYear": 2024
                }
                """;
    } // end buildStudentJson

    /**
     * Checks that POST /api/students returns 201 and the saved student.
     * @return none, the test passes when the status is 201 and the body has the new ID.
     */
    @Test
    void createStudentReturns201() throws Exception {
        when(gStudentService.createStudent(any(Student.class))).thenReturn(buildStudent());

        gMockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildStudentJson()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Sara"));
    } // end createStudentReturns201

    /**
     * Checks that POST /api/students returns 400 when a required field is missing.
     * @return none, the test passes when the status is 400 and the message names the field.
     */
    @Test
    void createStudentMissingFieldReturns400() throws Exception {
        when(gStudentService.createStudent(any(Student.class)))
                .thenThrow(new IllegalArgumentException("email is required."));

        gMockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildStudentJson()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("email is required."));
    } // end createStudentMissingFieldReturns400

    /**
     * Checks that POST /api/students returns 400 when the body is not valid JSON.
     * @return none, the test passes when the status is 400.
     */
    @Test
    void createStudentMalformedJsonReturns400() throws Exception {
        gMockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("this is not json"))
                .andExpect(status().isBadRequest());
    } // end createStudentMalformedJsonReturns400

    /**
     * Checks that GET /api/students returns 200 and the list of students.
     * @return none, the test passes when the status is 200 and the first student is returned.
     */
    @Test
    void getAllStudentsReturns200() throws Exception {
        when(gStudentService.getAllStudents()).thenReturn(List.of(buildStudent()));

        gMockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Sara"));
    } // end getAllStudentsReturns200

    /**
     * Checks that GET /api/students/{id} returns 200 and the matching student.
     * @return none, the test passes when the status is 200 and the ID matches.
     */
    @Test
    void getStudentByIdReturns200() throws Exception {
        when(gStudentService.getStudentById(1L)).thenReturn(buildStudent());

        gMockMvc.perform(get("/api/students/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    } // end getStudentByIdReturns200

    /**
     * Checks that GET /api/students/{id} returns 404 when the ID does not exist.
     * @return none, the test passes when the status is 404 and the message names the ID.
     */
    @Test
    void getStudentByIdMissingReturns404() throws Exception {
        when(gStudentService.getStudentById(999L))
                .thenThrow(new ResourceNotFoundException("Student not found with id: 999"));

        gMockMvc.perform(get("/api/students/{id}", 999))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Student not found with id: 999"));
    } // end getStudentByIdMissingReturns404

    /**
     * Checks that GET /api/students/{id} returns 400 when the ID is not a number.
     * @return none, the test passes when the status is 400.
     */
    @Test
    void getStudentByIdWrongTypeReturns400() throws Exception {
        gMockMvc.perform(get("/api/students/abc"))
                .andExpect(status().isBadRequest());
    } // end getStudentByIdWrongTypeReturns400

    /**
     * Checks that PUT /api/students/{id} returns 200 and the updated student.
     * @return none, the test passes when the status is 200 and the body has the student.
     */
    @Test
    void updateStudentReturns200() throws Exception {
        when(gStudentService.updateStudent(eq(1L), any(Student.class))).thenReturn(buildStudent());

        gMockMvc.perform(put("/api/students/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildStudentJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Feilmann"));
    } // end updateStudentReturns200

    /**
     * Checks that PUT /api/students/{id} returns 404 when the ID does not exist.
     * @return none, the test passes when the status is 404.
     */
    @Test
    void updateStudentMissingReturns404() throws Exception {
        when(gStudentService.updateStudent(eq(999L), any(Student.class)))
                .thenThrow(new ResourceNotFoundException("Student not found with id: 999"));

        gMockMvc.perform(put("/api/students/{id}", 999)
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildStudentJson()))
                .andExpect(status().isNotFound());
    } // end updateStudentMissingReturns404

    /**
     * Checks that DELETE /api/students/{id} returns 200 and a confirmation message.
     * @return none, the test passes when the status is 200 and the service delete method was called.
     */
    @Test
    void deleteStudentReturns200() throws Exception {
        gMockMvc.perform(delete("/api/students/{id}", 5))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Student deleted successfully."))
                .andExpect(jsonPath("$.id").value(5));

        verify(gStudentService).deleteStudentById(5L);
    } // end deleteStudentReturns200

    /**
     * Checks that DELETE /api/students/{id} returns 404 when the ID does not exist.
     * @return none, the test passes when the status is 404.
     */
    @Test
    void deleteStudentMissingReturns404() throws Exception {
        doThrow(new ResourceNotFoundException("Student not found with id: 999"))
                .when(gStudentService).deleteStudentById(999L);

        gMockMvc.perform(delete("/api/students/{id}", 999))
                .andExpect(status().isNotFound());
    } // end deleteStudentMissingReturns404

    /**
     * Checks that GET /api/students/major/{major} returns 200 and the matching students.
     * @return none, the test passes when the status is 200 and a student is returned.
     */
    @Test
    void getByMajorReturns200() throws Exception {
        when(gStudentService.getByMajor("Computer Science")).thenReturn(List.of(buildStudent()));

        gMockMvc.perform(get("/api/students/major/{major}", "Computer Science"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].major").value("Computer Science"));
    } // end getByMajorReturns200

    /**
     * Checks that GET /api/students/gpa/{gpa} returns 200 and the matching students.
     * @return none, the test passes when the status is 200 and a student is returned.
     */
    @Test
    void getByGpaReturns200() throws Exception {
        when(gStudentService.getByGpaGreaterThan(3.5)).thenReturn(List.of(buildStudent()));

        gMockMvc.perform(get("/api/students/gpa/{gpa}", 3.5))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].gpa").value(3.75));
    } // end getByGpaReturns200

    /**
     * Checks that GET /api/students/year/{year} returns 200 and the matching students.
     * @return none, the test passes when the status is 200 and a student is returned.
     */
    @Test
    void getByYearReturns200() throws Exception {
        when(gStudentService.getByEnrollmentYearGreaterThan(2022)).thenReturn(List.of(buildStudent()));

        gMockMvc.perform(get("/api/students/year/{year}", 2022))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].enrollmentYear").value(2024));
    } // end getByYearReturns200

    /**
     * Checks that GET /api/students/top3 returns 200 and the top students.
     * @return none, the test passes when the status is 200 and a student is returned.
     */
    @Test
    void getTop3Returns200() throws Exception {
        when(gStudentService.getTop3ByGpa()).thenReturn(List.of(buildStudent()));

        gMockMvc.perform(get("/api/students/top3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Sara"));
    } // end getTop3Returns200

    /**
     * Checks that GET /api/students/sort uses asc when no direction is given.
     * @return none, the test passes when the status is 200 and the service received asc.
     */
    @Test
    void sortDefaultsToAscending() throws Exception {
        when(gStudentService.getAllSortedByLastName("asc")).thenReturn(List.of(buildStudent()));

        gMockMvc.perform(get("/api/students/sort"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lastName").value("Feilmann"));
    } // end sortDefaultsToAscending

    /**
     * Checks that GET /api/students/sort passes the desc direction to the service.
     * @return none, the test passes when the status is 200.
     */
    @Test
    void sortDescendingReturns200() throws Exception {
        when(gStudentService.getAllSortedByLastName("desc")).thenReturn(List.of(buildStudent()));

        gMockMvc.perform(get("/api/students/sort").param("direction", "desc"))
                .andExpect(status().isOk());
    } // end sortDescendingReturns200

    /**
     * Checks that GET /api/students/sort returns 400 for a direction that is not asc or desc.
     * @return none, the test passes when the status is 400.
     */
    @Test
    void sortInvalidDirectionReturns400() throws Exception {
        when(gStudentService.getAllSortedByLastName("up"))
                .thenThrow(new IllegalArgumentException("Invalid value 'up' for orders given."));

        gMockMvc.perform(get("/api/students/sort").param("direction", "up"))
                .andExpect(status().isBadRequest());
    } // end sortInvalidDirectionReturns400

    /**
     * Checks that GET /api/students/page returns 200 and one page of students.
     * @return none, the test passes when the status is 200 and the content holds a student.
     */
    @Test
    void getPagedReturns200() throws Exception {
        // A real PageRequest is needed because an unpaged page cannot be written as JSON.
        PageImpl<Student> lPage = new PageImpl<>(List.of(buildStudent()), PageRequest.of(0, 5), 1);
        when(gStudentService.getStudentsPaged(0, 5)).thenReturn(lPage);

        gMockMvc.perform(get("/api/students/page").param("page", "0").param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName").value("Sara"));
    } // end getPagedReturns200

    /**
     * Checks that GET /api/students/major-jpql/{major} returns 200 and the matching students.
     * @return none, the test passes when the status is 200 and a student is returned.
     */
    @Test
    void getByMajorJpqlReturns200() throws Exception {
        when(gStudentService.getByMajorJpql("Cybersecurity")).thenReturn(List.of(buildStudent()));

        gMockMvc.perform(get("/api/students/major-jpql/{major}", "Cybersecurity"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Sara"));
    } // end getByMajorJpqlReturns200

    /**
     * Checks that DELETE /api/students/year/{year}/jpql returns 200 and the number of deleted students.
     * @return none, the test passes when the status is 200 and deletedCount is 1.
     */
    @Test
    void deleteByYearJpqlReturns200() throws Exception {
        when(gStudentService.deleteByEnrollmentYearJpql(2019)).thenReturn(1);

        gMockMvc.perform(delete("/api/students/year/{year}/jpql", 2019))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deletedCount").value(1));
    } // end deleteByYearJpqlReturns200
} // end StudentControllerTest
