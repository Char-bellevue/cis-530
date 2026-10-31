/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week8.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.bellevue.cis530.week8.entity.Student;
import edu.bellevue.cis530.week8.entity.StudentProfile;
import edu.bellevue.cis530.week8.repository.StudentRepository;
import edu.bellevue.cis530.week8.service.impl.StudentServiceImpl;

/**
 * Unit tests for the service layer of the Student Services API.
 * The repository is mocked so that no real database is used.
 */
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    // The id used by every sample student in these tests.
    private final Long STUDENT_ID = 1L;

    // The mocked repository that replaces the real database.
    @Mock
    private StudentRepository gStudentRepository;

    // The real service object that receives the mocked repository.
    @InjectMocks
    private StudentServiceImpl gStudentService;

    // The sample student that most tests use as input.
    private Student gStudent;

    /**
     * Builds a sample student that includes a valid profile.
     *
     * @param id Long the id to give the sample student.
     * @return Student a sample student with a profile attached.
     */
    private Student buildStudent(Long id) {
        StudentProfile lProfile = new StudentProfile();
        lProfile.setPhone("555-555-1234");
        lProfile.setAddress("100 Main Street");
        lProfile.setCity("Omaha");
        lProfile.setState("NE");
        lProfile.setZip("68101");
        lProfile.setEmergencyContactName("Charles Babbage");
        lProfile.setEmergencyContactPhone("555-555-9876");

        Student lStudent = new Student();
        lStudent.setId(id);
        lStudent.setFirstName("Ada");
        lStudent.setLastName("Lovelace");
        lStudent.setEmail("ada.lovelace@example.com");
        lStudent.setMajor("Computer Science");
        lStudent.setGpa(new BigDecimal("3.85"));
        lStudent.setEnrollmentYear(2024);
        lStudent.setProfile(lProfile);
        return lStudent;
    } // end buildStudent

    /**
     * Builds a fresh sample student before every test runs.
     * No parameters.
     * @return nothing; the gStudent field is ready for use by each test.
     */
    @BeforeEach
    void setUp() {
        gStudent = buildStudent(STUDENT_ID);
    } // end setUp

    /**
     * Verifies that createStudent links the profile and saves the student through the repository.
     * No parameters.
     * @return nothing; the test passes when the saved student is returned and saveAndFlush is called once.
     */
    @Test
    void createStudent() {
        Student lSavedStudent = buildStudent(STUDENT_ID); // Simulates the row that the database returns.
        when(gStudentRepository.saveAndFlush(gStudent)).thenReturn(lSavedStudent); // Define the mocked save behavior.

        Student lResultStudent = gStudentService.createStudent(gStudent); // Call the service method under test.

        assertNotNull(lResultStudent);
        assertEquals(STUDENT_ID, lResultStudent.getId());
        assertEquals("Ada", lResultStudent.getFirstName());
        assertSame(gStudent, gStudent.getProfile().getStudent()); // The service must set the inverse side.
        verify(gStudentRepository).saveAndFlush(gStudent); // Confirm the repository interaction.
    } // end createStudent

    /**
     * Verifies that getAllStudents returns every student the repository holds.
     * No parameters.
     * @return nothing; the test passes when the returned list matches the mocked list.
     */
    @Test
    void getAllStudents() {
        List<Student> lStudentList = new ArrayList<>();
        lStudentList.add(gStudent);
        when(gStudentRepository.findAll()).thenReturn(lStudentList); // Define the mocked findAll behavior.

        List<Student> lResultList = gStudentService.getAllStudents(); // Call the service method under test.

        assertEquals(1, lResultList.size());
        assertTrue(lResultList.contains(gStudent));
        verify(gStudentRepository).findAll(); // Confirm the repository interaction.
    } // end getAllStudents

    /**
     * Verifies that getStudentById returns the student with the matching id.
     * No parameters.
     * @return nothing; the test passes when the returned student has the expected id.
     */
    @Test
    void getStudentById() {
        when(gStudentRepository.findById(STUDENT_ID)).thenReturn(Optional.of(gStudent)); // Define the mocked lookup.

        Student lFoundStudent = gStudentService.getStudentById(STUDENT_ID); // Call the service method under test.

        assertNotNull(lFoundStudent);
        assertEquals(STUDENT_ID, lFoundStudent.getId());
        verify(gStudentRepository).findById(STUDENT_ID); // Confirm the repository interaction.
    } // end getStudentById

    /**
     * Verifies that deleteStudent finds the student and then deletes it through the repository.
     * No parameters.
     * @return nothing; the test passes when findById and delete are each called once.
     */
    @Test
    void deleteStudent() {
        when(gStudentRepository.findById(STUDENT_ID)).thenReturn(Optional.of(gStudent)); // The student must exist.

        gStudentService.deleteStudent(STUDENT_ID); // Call the service method under test.

        verify(gStudentRepository).findById(STUDENT_ID); // Confirm the lookup happened.
        verify(gStudentRepository).delete(gStudent); // Confirm the delete happened.
    } // end deleteStudent
} // end StudentServiceTest
