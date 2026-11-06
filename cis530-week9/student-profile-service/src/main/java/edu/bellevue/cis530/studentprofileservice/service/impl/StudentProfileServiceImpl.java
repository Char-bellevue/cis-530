/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.studentprofileservice.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.bellevue.cis530.studentprofileservice.entity.StudentProfile;
import edu.bellevue.cis530.studentprofileservice.exception.ResourceNotFoundException;
import edu.bellevue.cis530.studentprofileservice.repository.StudentProfileRepository;
import edu.bellevue.cis530.studentprofileservice.service.StudentProfileService;
import lombok.RequiredArgsConstructor;

/**
 * Default implementation of the profile service that uses the profile repository.
 */
@Service
@RequiredArgsConstructor
public class StudentProfileServiceImpl implements StudentProfileService {

    // The start of the message used when a profile cannot be found by its own id.
    private static final String PROFILE_NOT_FOUND_MESSAGE = "Profile not found with id: ";

    // The start of the message used when a student has no profile.
    private static final String STUDENT_PROFILE_NOT_FOUND_MESSAGE = "Profile not found for student id: ";

    // The repository used to read and write profiles; Lombok injects it through the constructor.
    private final StudentProfileRepository gStudentProfileRepository;

    /**
     * Creates a new profile.
     *
     * @param profile StudentProfile the validated profile to save.
     * @return StudentProfile the saved profile with the generated id.
     */
    @Override
    public StudentProfile createProfile(StudentProfile profile) {
        return gStudentProfileRepository.save(profile); // Insert the row and return the saved entity.
    } // end createProfile

    /**
     * Gets every profile in the database.
     *
     * @return List&lt;StudentProfile&gt; all profiles, or an empty list when none exist.
     */
    @Override
    public List<StudentProfile> getAllProfiles() {
        return gStudentProfileRepository.findAll();
    } // end getAllProfiles

    /**
     * Gets one profile by its own id.
     *
     * @param id Long the id of the profile to find.
     * @return StudentProfile the profile that has the given id.
     */
    @Override
    public StudentProfile getProfileById(Long id) {
        return gStudentProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PROFILE_NOT_FOUND_MESSAGE + id)); // Missing id gives a 404.
    } // end getProfileById

    /**
     * Gets the profile that belongs to one student.
     *
     * @param studentId Long the id of the student in the Student Service.
     * @return StudentProfile the profile of the student.
     */
    @Override
    public StudentProfile getProfileByStudentId(Long studentId) {
        return gStudentProfileRepository.findByStudentId(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(STUDENT_PROFILE_NOT_FOUND_MESSAGE + studentId)); // No match gives a 404.
    } // end getProfileByStudentId

    /**
     * Deletes a profile.
     *
     * @param id Long the id of the profile to delete.
     * @return nothing; the profile is removed.
     */
    @Override
    public void deleteProfile(Long id) {
        gStudentProfileRepository.deleteById(id); // Remove the row that has the given id.
    } // end deleteProfile
} // end StudentProfileServiceImpl
