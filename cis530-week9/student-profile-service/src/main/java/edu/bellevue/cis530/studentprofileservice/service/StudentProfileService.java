/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.studentprofileservice.service;

import java.util.List;

import edu.bellevue.cis530.studentprofileservice.entity.StudentProfile;

/**
 * Service contract that holds the business logic for student profiles.
 */
public interface StudentProfileService {

    /**
     * Creates a new profile.
     *
     * @param profile StudentProfile the validated profile to save.
     * @return StudentProfile the saved profile with the generated id.
     */
    StudentProfile createProfile(StudentProfile profile);

    /**
     * Gets every profile in the database.
     *
     * @return List&lt;StudentProfile&gt; all profiles, or an empty list when none exist.
     */
    List<StudentProfile> getAllProfiles();

    /**
     * Gets one profile by its own id.
     *
     * @param id Long the id of the profile to find.
     * @return StudentProfile the profile that has the given id.
     */
    StudentProfile getProfileById(Long id);

    /**
     * Gets the profile that belongs to one student.
     *
     * @param studentId Long the id of the student in the Student Service.
     * @return StudentProfile the profile of the student.
     */
    StudentProfile getProfileByStudentId(Long studentId);

    /**
     * Deletes a profile.
     *
     * @param id Long the id of the profile to delete.
     * @return nothing; the profile is removed.
     */
    void deleteProfile(Long id);
} // end StudentProfileService
