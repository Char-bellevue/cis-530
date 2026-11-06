/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.studentprofileservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import edu.bellevue.cis530.studentprofileservice.entity.StudentProfile;
import edu.bellevue.cis530.studentprofileservice.exception.ResourceNotFoundException;
import edu.bellevue.cis530.studentprofileservice.repository.StudentProfileRepository;
import edu.bellevue.cis530.studentprofileservice.service.impl.StudentProfileServiceImpl;

/**
 * Unit tests for the service layer of the Student Profile Service.
 * The repository is mocked so that no real database is used.
 */
@ExtendWith(MockitoExtension.class)
class StudentProfileServiceTest {

    // The id used by every sample profile in these tests.
    private final Long PROFILE_ID = 1L;

    // The student id used by every sample profile in these tests.
    private final Long STUDENT_ID = 1L;

    // The mocked repository that replaces the real database.
    @Mock
    private StudentProfileRepository gStudentProfileRepository;

    // The real service object that receives the mocked repository.
    @InjectMocks
    private StudentProfileServiceImpl gStudentProfileService;

    // The sample profile that most tests use as input.
    private StudentProfile gProfile;

    /**
     * Builds a fresh sample profile before every test runs.
     * No parameters.
     * @return nothing; the gProfile field is ready for use by each test.
     */
    @BeforeEach
    void setUp() {
        gProfile = new StudentProfile(PROFILE_ID, STUDENT_ID, "Computer Science", new BigDecimal("3.75"),
                "Enjoys backend development and databases.");
    } // end setUp

    /**
     * Verifies that createProfile saves the profile through the repository.
     * No parameters.
     * @return nothing; the test passes when the saved profile is returned and save is called once.
     */
    @Test
    void createProfile() {
        when(gStudentProfileRepository.save(gProfile)).thenReturn(gProfile); // Define the mocked save behavior.

        StudentProfile lResultProfile = gStudentProfileService.createProfile(gProfile); // Call the method under test.

        assertNotNull(lResultProfile);
        assertEquals(PROFILE_ID, lResultProfile.getId());
        assertEquals("Computer Science", lResultProfile.getMajor());
        verify(gStudentProfileRepository).save(gProfile); // Confirm the repository interaction.
    } // end createProfile

    /**
     * Verifies that getAllProfiles returns every profile the repository holds.
     * No parameters.
     * @return nothing; the test passes when the returned list matches the mocked list.
     */
    @Test
    void getAllProfiles() {
        List<StudentProfile> lProfileList = new ArrayList<>();
        lProfileList.add(gProfile);
        when(gStudentProfileRepository.findAll()).thenReturn(lProfileList); // Define the mocked findAll behavior.

        List<StudentProfile> lResultList = gStudentProfileService.getAllProfiles(); // Call the method under test.

        assertEquals(1, lResultList.size());
        assertTrue(lResultList.contains(gProfile));
        verify(gStudentProfileRepository).findAll(); // Confirm the repository interaction.
    } // end getAllProfiles

    /**
     * Verifies that getProfileById returns the profile with the matching id.
     * No parameters.
     * @return nothing; the test passes when the returned profile has the expected id.
     */
    @Test
    void getProfileById() {
        when(gStudentProfileRepository.findById(PROFILE_ID)).thenReturn(Optional.of(gProfile)); // Define the lookup.

        StudentProfile lFoundProfile = gStudentProfileService.getProfileById(PROFILE_ID); // Call the method under test.

        assertNotNull(lFoundProfile);
        assertEquals(PROFILE_ID, lFoundProfile.getId());
        verify(gStudentProfileRepository).findById(PROFILE_ID); // Confirm the repository interaction.
    } // end getProfileById

    /**
     * Verifies that getProfileById throws ResourceNotFoundException when the id does not exist.
     * No parameters.
     * @return nothing; the test passes when the exception is thrown with the expected message.
     */
    @Test
    void getProfileByIdNotFound() {
        Long lMissingId = 99L; // An id that no sample profile uses.
        when(gStudentProfileRepository.findById(lMissingId)).thenReturn(Optional.empty()); // The lookup finds nothing.

        ResourceNotFoundException lException = assertThrows(ResourceNotFoundException.class,
                () -> gStudentProfileService.getProfileById(lMissingId)); // Call the method under test.

        assertEquals("Profile not found with id: 99", lException.getMessage());
    } // end getProfileByIdNotFound

    /**
     * Verifies that getProfileByStudentId returns the profile that belongs to the given student.
     * No parameters.
     * @return nothing; the test passes when the returned profile has the expected student id.
     */
    @Test
    void getProfileByStudentId() {
        when(gStudentProfileRepository.findByStudentId(STUDENT_ID)).thenReturn(Optional.of(gProfile));

        StudentProfile lFoundProfile = gStudentProfileService.getProfileByStudentId(STUDENT_ID); // Call the method.

        assertNotNull(lFoundProfile);
        assertEquals(STUDENT_ID, lFoundProfile.getStudentId());
        verify(gStudentProfileRepository).findByStudentId(STUDENT_ID); // Confirm the repository interaction.
    } // end getProfileByStudentId

    /**
     * Verifies that getProfileByStudentId throws ResourceNotFoundException when the student has no profile.
     * No parameters.
     * @return nothing; the test passes when the exception is thrown with the expected message.
     */
    @Test
    void getProfileByStudentIdNotFound() {
        Long lMissingStudentId = 99L; // A student id that no sample profile uses.
        when(gStudentProfileRepository.findByStudentId(lMissingStudentId)).thenReturn(Optional.empty());

        ResourceNotFoundException lException = assertThrows(ResourceNotFoundException.class,
                () -> gStudentProfileService.getProfileByStudentId(lMissingStudentId)); // Call the method.

        assertEquals("Profile not found for student id: 99", lException.getMessage());
    } // end getProfileByStudentIdNotFound

    /**
     * Verifies that deleteProfile deletes the profile through the repository.
     * No parameters.
     * @return nothing; the test passes when deleteById is called once with the given id.
     */
    @Test
    void deleteProfile() {
        gStudentProfileService.deleteProfile(PROFILE_ID); // Call the service method under test.

        verify(gStudentProfileRepository).deleteById(PROFILE_ID); // Confirm the delete happened.
    } // end deleteProfile
} // end StudentProfileServiceTest
