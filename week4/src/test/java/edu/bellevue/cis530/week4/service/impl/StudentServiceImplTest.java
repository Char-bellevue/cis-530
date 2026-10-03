/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week4.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import edu.bellevue.cis530.week4.entity.Student;
import edu.bellevue.cis530.week4.exception.ResourceNotFoundException;
import edu.bellevue.cis530.week4.repository.StudentRepository;

/**
 * Test class for StudentServiceImpl that uses a mocked repository so no database is needed.
 */
@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    // Mocked repository that replaces the real database access.
    @Mock
    private StudentRepository gStudentRepository;

    // Service under test, which receives the mocked repository.
    @InjectMocks
    private StudentServiceImpl gStudentService;

    /**
     * Builds a valid student that the tests can use.
     * @return Student a student with every required field filled in.
     */
    private Student buildStudent() {
        Student lStudent = new Student();
        lStudent.setFirstName("Sara");
        lStudent.setLastName("Feilmann");
        lStudent.setEmail("sara.feilmann@example.com");
        lStudent.setMajor("Computer Science");
        lStudent.setGpa(3.75);
        lStudent.setEnrollmentYear(2024);
        return lStudent;
    } // end buildStudent

    /**
     * Checks that createStudent clears any client-supplied ID and saves the student.
     * @return none, the test passes when the ID is null and save was called.
     */
    @Test
    void createStudentClearsIdAndSaves() {
        Student lStudent = buildStudent();
        lStudent.setId(99L); // Simulate a client that sends an ID.
        when(gStudentRepository.save(lStudent)).thenReturn(lStudent);

        Student lResult = gStudentService.createStudent(lStudent);

        assertNull(lStudent.getId());
        assertSame(lStudent, lResult);
        verify(gStudentRepository).save(lStudent);
    } // end createStudentClearsIdAndSaves

    /**
     * Checks that createStudent rejects a student with a missing email.
     * @return none, the test passes when an IllegalArgumentException is thrown and nothing is saved.
     */
    @Test
    void createStudentMissingEmailThrows() {
        Student lStudent = buildStudent();
        lStudent.setEmail(null);

        IllegalArgumentException lException = assertThrows(IllegalArgumentException.class,
                () -> gStudentService.createStudent(lStudent));

        assertEquals("email is required.", lException.getMessage());
        verifyNoInteractions(gStudentRepository);
    } // end createStudentMissingEmailThrows

    /**
     * Checks that createStudent rejects a student with a blank first name.
     * @return none, the test passes when an IllegalArgumentException is thrown and nothing is saved.
     */
    @Test
    void createStudentBlankFirstNameThrows() {
        Student lStudent = buildStudent();
        lStudent.setFirstName("   ");

        IllegalArgumentException lException = assertThrows(IllegalArgumentException.class,
                () -> gStudentService.createStudent(lStudent));

        assertEquals("firstName is required.", lException.getMessage());
        verifyNoInteractions(gStudentRepository);
    } // end createStudentBlankFirstNameThrows

    /**
     * Checks that createStudent rejects a student with a missing GPA.
     * @return none, the test passes when an IllegalArgumentException is thrown and nothing is saved.
     */
    @Test
    void createStudentMissingGpaThrows() {
        Student lStudent = buildStudent();
        lStudent.setGpa(null);

        IllegalArgumentException lException = assertThrows(IllegalArgumentException.class,
                () -> gStudentService.createStudent(lStudent));

        assertEquals("gpa is required.", lException.getMessage());
        verifyNoInteractions(gStudentRepository);
    } // end createStudentMissingGpaThrows

    /**
     * Checks that getStudentById returns the student when the ID exists.
     * @return none, the test passes when the repository student is returned.
     */
    @Test
    void getStudentByIdReturnsStudent() {
        Student lStudent = buildStudent();
        when(gStudentRepository.findById(1L)).thenReturn(Optional.of(lStudent));

        assertSame(lStudent, gStudentService.getStudentById(1L));
    } // end getStudentByIdReturnsStudent

    /**
     * Checks that getStudentById throws ResourceNotFoundException when the ID does not exist.
     * @return none, the test passes when the exception message names the missing ID.
     */
    @Test
    void getStudentByIdMissingThrows() {
        when(gStudentRepository.findById(999L)).thenReturn(Optional.empty());

        ResourceNotFoundException lException = assertThrows(ResourceNotFoundException.class,
                () -> gStudentService.getStudentById(999L));

        assertEquals("Student not found with id: 999", lException.getMessage());
    } // end getStudentByIdMissingThrows

    /**
     * Checks that getAllStudents returns every student from the repository.
     * @return none, the test passes when the repository list is returned.
     */
    @Test
    void getAllStudentsReturnsList() {
        List<Student> lStudents = List.of(buildStudent());
        when(gStudentRepository.findAll()).thenReturn(lStudents);

        assertEquals(lStudents, gStudentService.getAllStudents());
    } // end getAllStudentsReturnsList

    /**
     * Checks that updateStudent copies the new values onto the existing student and saves it.
     * @return none, the test passes when the existing student holds the new values.
     */
    @Test
    void updateStudentChangesFields() {
        Student lExistingStudent = buildStudent();
        lExistingStudent.setId(1L);
        Student lNewData = buildStudent();
        lNewData.setFirstName("Updated");
        lNewData.setGpa(3.9);
        when(gStudentRepository.findById(1L)).thenReturn(Optional.of(lExistingStudent));
        when(gStudentRepository.save(lExistingStudent)).thenReturn(lExistingStudent);

        Student lResult = gStudentService.updateStudent(1L, lNewData);

        assertEquals("Updated", lResult.getFirstName());
        assertEquals(3.9, lResult.getGpa());
        verify(gStudentRepository).save(lExistingStudent);
    } // end updateStudentChangesFields

    /**
     * Checks that updateStudent throws ResourceNotFoundException and saves nothing when the ID is missing.
     * @return none, the test passes when the exception is thrown and save is never called.
     */
    @Test
    void updateStudentMissingIdThrows() {
        when(gStudentRepository.findById(999L)).thenReturn(Optional.empty());
        Student lNewData = buildStudent();

        assertThrows(ResourceNotFoundException.class, () -> gStudentService.updateStudent(999L, lNewData));

        verify(gStudentRepository, never()).save(any(Student.class));
    } // end updateStudentMissingIdThrows

    /**
     * Checks that deleteStudentById deletes the student when the ID exists.
     * @return none, the test passes when the repository delete method is called.
     */
    @Test
    void deleteStudentByIdDeletes() {
        Student lStudent = buildStudent();
        when(gStudentRepository.findById(5L)).thenReturn(Optional.of(lStudent));

        gStudentService.deleteStudentById(5L);

        verify(gStudentRepository).delete(lStudent);
    } // end deleteStudentByIdDeletes

    /**
     * Checks that deleteStudentById throws ResourceNotFoundException and deletes nothing when the ID is missing.
     * @return none, the test passes when the exception is thrown and delete is never called.
     */
    @Test
    void deleteStudentByIdMissingThrows() {
        when(gStudentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> gStudentService.deleteStudentById(999L));

        verify(gStudentRepository, never()).delete(any(Student.class));
    } // end deleteStudentByIdMissingThrows

    /**
     * Checks that getByMajor returns the repository result for the major.
     * @return none, the test passes when the repository list is returned.
     */
    @Test
    void getByMajorReturnsList() {
        List<Student> lStudents = List.of(buildStudent());
        when(gStudentRepository.findByMajor("Computer Science")).thenReturn(lStudents);

        assertEquals(lStudents, gStudentService.getByMajor("Computer Science"));
    } // end getByMajorReturnsList

    /**
     * Checks that getByGpaGreaterThan returns the repository result for the GPA.
     * @return none, the test passes when the repository list is returned.
     */
    @Test
    void getByGpaGreaterThanReturnsList() {
        List<Student> lStudents = List.of(buildStudent());
        when(gStudentRepository.findByGpaGreaterThan(3.5)).thenReturn(lStudents);

        assertEquals(lStudents, gStudentService.getByGpaGreaterThan(3.5));
    } // end getByGpaGreaterThanReturnsList

    /**
     * Checks that getByEnrollmentYearGreaterThan returns the repository result for the year.
     * @return none, the test passes when the repository list is returned.
     */
    @Test
    void getByEnrollmentYearGreaterThanReturnsList() {
        List<Student> lStudents = List.of(buildStudent());
        when(gStudentRepository.findByEnrollmentYearGreaterThan(2022)).thenReturn(lStudents);

        assertEquals(lStudents, gStudentService.getByEnrollmentYearGreaterThan(2022));
    } // end getByEnrollmentYearGreaterThanReturnsList

    /**
     * Checks that getTop3ByGpa returns the repository result.
     * @return none, the test passes when the repository list is returned.
     */
    @Test
    void getTop3ByGpaReturnsList() {
        List<Student> lStudents = List.of(buildStudent());
        when(gStudentRepository.findTop3ByOrderByGpaDesc()).thenReturn(lStudents);

        assertEquals(lStudents, gStudentService.getTop3ByGpa());
    } // end getTop3ByGpaReturnsList

    /**
     * Checks that getAllSortedByLastName sorts by lastName in ascending order for asc.
     * @return none, the test passes when the repository receives an ascending lastName sort.
     */
    @Test
    void getAllSortedByLastNameAscending() {
        List<Student> lStudents = List.of(buildStudent());
        when(gStudentRepository.findAll(any(Sort.class))).thenReturn(lStudents);

        List<Student> lResult = gStudentService.getAllSortedByLastName("asc");

        ArgumentCaptor<Sort> lSortCaptor = ArgumentCaptor.forClass(Sort.class);
        verify(gStudentRepository).findAll(lSortCaptor.capture());
        assertEquals(lStudents, lResult);
        assertEquals(Sort.Direction.ASC, lSortCaptor.getValue().getOrderFor("lastName").getDirection());
    } // end getAllSortedByLastNameAscending

    /**
     * Checks that getAllSortedByLastName sorts by lastName in descending order for desc.
     * @return none, the test passes when the repository receives a descending lastName sort.
     */
    @Test
    void getAllSortedByLastNameDescending() {
        when(gStudentRepository.findAll(any(Sort.class))).thenReturn(List.of(buildStudent()));

        gStudentService.getAllSortedByLastName("desc");

        ArgumentCaptor<Sort> lSortCaptor = ArgumentCaptor.forClass(Sort.class);
        verify(gStudentRepository).findAll(lSortCaptor.capture());
        assertEquals(Sort.Direction.DESC, lSortCaptor.getValue().getOrderFor("lastName").getDirection());
    } // end getAllSortedByLastNameDescending

    /**
     * Checks that getAllSortedByLastName rejects a direction that is not asc or desc.
     * @return none, the test passes when an IllegalArgumentException is thrown.
     */
    @Test
    void getAllSortedByLastNameInvalidDirectionThrows() {
        assertThrows(IllegalArgumentException.class, () -> gStudentService.getAllSortedByLastName("up"));

        verifyNoInteractions(gStudentRepository);
    } // end getAllSortedByLastNameInvalidDirectionThrows

    /**
     * Checks that getStudentsPaged sends the page number and size to the repository.
     * @return none, the test passes when the pageable has page 1 and size 5.
     */
    @Test
    void getStudentsPagedUsesPageAndSize() {
        Page<Student> lPage = new PageImpl<>(List.of(buildStudent()));
        when(gStudentRepository.findAll(any(Pageable.class))).thenReturn(lPage);

        Page<Student> lResult = gStudentService.getStudentsPaged(1, 5);

        ArgumentCaptor<Pageable> lPageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(gStudentRepository).findAll(lPageableCaptor.capture());
        assertSame(lPage, lResult);
        assertEquals(1, lPageableCaptor.getValue().getPageNumber());
        assertEquals(5, lPageableCaptor.getValue().getPageSize());
    } // end getStudentsPagedUsesPageAndSize

    /**
     * Checks that getStudentsPaged rejects a negative page number.
     * @return none, the test passes when an IllegalArgumentException is thrown.
     */
    @Test
    void getStudentsPagedNegativePageThrows() {
        assertThrows(IllegalArgumentException.class, () -> gStudentService.getStudentsPaged(-1, 5));

        verifyNoInteractions(gStudentRepository);
    } // end getStudentsPagedNegativePageThrows

    /**
     * Checks that getByMajorJpql returns the result of the custom JPQL query.
     * @return none, the test passes when the repository list is returned.
     */
    @Test
    void getByMajorJpqlReturnsList() {
        List<Student> lStudents = List.of(buildStudent());
        when(gStudentRepository.findStudentsByMajorJpql("Cybersecurity")).thenReturn(lStudents);

        assertEquals(lStudents, gStudentService.getByMajorJpql("Cybersecurity"));
    } // end getByMajorJpqlReturnsList

    /**
     * Checks that deleteByEnrollmentYearJpql returns the number of deleted rows.
     * @return none, the test passes when the repository count is returned.
     */
    @Test
    void deleteByEnrollmentYearJpqlReturnsCount() {
        when(gStudentRepository.deleteStudentsByEnrollmentYearJpql(2019)).thenReturn(2);

        assertEquals(2, gStudentService.deleteByEnrollmentYearJpql(2019));
    } // end deleteByEnrollmentYearJpqlReturnsCount
} // end StudentServiceImplTest
