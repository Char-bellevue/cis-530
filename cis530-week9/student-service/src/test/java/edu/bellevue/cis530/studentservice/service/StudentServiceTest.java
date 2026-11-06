/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.studentservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.bellevue.cis530.studentservice.entity.Student;
import edu.bellevue.cis530.studentservice.exception.ResourceNotFoundException;
import edu.bellevue.cis530.studentservice.repository.StudentRepository;
import edu.bellevue.cis530.studentservice.service.impl.StudentServiceImpl;

/**
 * Unit tests for the service layer of the Student Service.
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
     * Builds a fresh sample student before every test runs.
     * No parameters.
     * @return nothing; the gStudent field is ready for use by each test.
     */
    @BeforeEach
    void setUp() {
        gStudent = new Student(STUDENT_ID, "Ali", "Rezaei", "ali.rezaei@example.com");
    } // end setUp

    /**
     * Verifies that createStudent saves the student through the repository.
     * No parameters.
     * @return nothing; the test passes when the saved student is returned and save is called once.
     */
    @Test
    void createStudent() {
        when(gStudentRepository.save(gStudent)).thenReturn(gStudent); // Define the mocked save behavior.

        Student lResultStudent = gStudentService.createStudent(gStudent); // Call the service method under test.

        assertNotNull(lResultStudent);
        assertEquals(STUDENT_ID, lResultStudent.getId());
        assertEquals("Ali", lResultStudent.getFirstName());
        verify(gStudentRepository).save(gStudent); // Confirm the repository interaction.
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
     * Verifies that getStudentById throws ResourceNotFoundException when the id does not exist.
     * No parameters.
     * @return nothing; the test passes when the exception is thrown with the expected message.
     */
    @Test
    void getStudentByIdNotFound() {
        Long lMissingId = 99L; // An id that no sample student uses.
        when(gStudentRepository.findById(lMissingId)).thenReturn(Optional.empty()); // The lookup finds nothing.

        ResourceNotFoundException lException = assertThrows(ResourceNotFoundException.class,
                () -> gStudentService.getStudentById(lMissingId)); // Call the service method under test.

        assertEquals("Student not found with id: 99", lException.getMessage());
    } // end getStudentByIdNotFound

    /**
     * Verifies that deleteStudent deletes the student through the repository.
     * No parameters.
     * @return nothing; the test passes when deleteById is called once with the given id.
     */
    @Test
    void deleteStudent() {
        gStudentService.deleteStudent(STUDENT_ID); // Call the service method under test.

        verify(gStudentRepository).deleteById(STUDENT_ID); // Confirm the delete happened.
    } // end deleteStudent
} // end StudentServiceTest
