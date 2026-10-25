/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week7;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of the student service that uses the student repository.
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    // The start of the message used when a student cannot be found.
    private static final String STUDENT_NOT_FOUND_MESSAGE = "Student not found with id: ";

    // The start of the message used when a student has no profile.
    private static final String PROFILE_NOT_FOUND_MESSAGE = "Profile not found for student id: ";

    // The repository used to read and write students and, through cascade, their profiles.
    private final StudentRepository gStudentRepository;

    /**
     * Creates the service with the repository that Spring injects.
     *
     * @param studentRepository StudentRepository the repository used for student data access.
     * @return nothing; a new StudentServiceImpl object is constructed.
     */
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.gStudentRepository = studentRepository; // Keep the injected repository for later use.
    } // end StudentServiceImpl

    /**
     * Creates a new student together with its profile.
     *
     * @param student Student the validated student and profile to save.
     * @return Student the saved student with generated ids.
     */
    @Override
    public Student createStudent(Student student) {
        student.setId(null); // Make sure a new row is inserted instead of updating an existing one.
        StudentProfile lProfile = student.getProfile();
        lProfile.setId(null);
        lProfile.setStudent(student); // Set the inverse side because JSON does not carry it.
        return gStudentRepository.saveAndFlush(student); // Cascade saves the profile as well.
    } // end createStudent

    /**
     * Gets every student in the database.
     *
     * @return List&lt;Student&gt; all students, or an empty list when none exist.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return gStudentRepository.findAll();
    } // end getAllStudents

    /**
     * Gets one student by id.
     *
     * @param id Long the id of the student to find.
     * @return Student the student that has the given id.
     */
    @Override
    @Transactional(readOnly = true)
    public Student getStudentById(Long id) {
        return gStudentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(STUDENT_NOT_FOUND_MESSAGE + id));
    } // end getStudentById

    /**
     * Updates an existing student together with its profile.
     *
     * @param id Long the id of the student to update.
     * @param student Student the validated new student and profile values.
     * @return Student the updated student.
     */
    @Override
    public Student updateStudent(Long id, Student student) {
        Student lExistingStudent = getStudentById(id); // Throws a not-found exception when the id is missing.
        lExistingStudent.setFirstName(student.getFirstName());
        lExistingStudent.setLastName(student.getLastName());
        lExistingStudent.setEmail(student.getEmail());
        lExistingStudent.setMajor(student.getMajor());
        lExistingStudent.setGpa(student.getGpa());
        lExistingStudent.setEnrollmentYear(student.getEnrollmentYear());

        StudentProfile lNewProfile = student.getProfile();
        StudentProfile lExistingProfile = lExistingStudent.getProfile();
        if (lExistingProfile == null) {
            lNewProfile.setId(null); // The student has no profile yet, so attach the new one.
            lNewProfile.setStudent(lExistingStudent);
            lExistingStudent.setProfile(lNewProfile);
        } else {
            lExistingProfile.setPhone(lNewProfile.getPhone()); // Update the existing profile in place.
            lExistingProfile.setAddress(lNewProfile.getAddress());
            lExistingProfile.setCity(lNewProfile.getCity());
            lExistingProfile.setState(lNewProfile.getState());
            lExistingProfile.setZip(lNewProfile.getZip());
            lExistingProfile.setEmergencyContactName(lNewProfile.getEmergencyContactName());
            lExistingProfile.setEmergencyContactPhone(lNewProfile.getEmergencyContactPhone());
        }
        return gStudentRepository.saveAndFlush(lExistingStudent); // Flush now so database errors surface here.
    } // end updateStudent

    /**
     * Deletes a student together with its profile.
     *
     * @param id Long the id of the student to delete.
     * @return nothing; the student and profile are removed.
     */
    @Override
    public void deleteStudent(Long id) {
        Student lStudent = getStudentById(id); // Throws a not-found exception when the id is missing.
        gStudentRepository.delete(lStudent); // Cascade and orphan removal delete the profile too.
    } // end deleteStudent

    /**
     * Gets the profile that belongs to one student.
     *
     * @param id Long the id of the student whose profile is wanted.
     * @return StudentProfile the profile of the student.
     */
    @Override
    @Transactional(readOnly = true)
    public StudentProfile getProfileByStudentId(Long id) {
        Student lStudent = getStudentById(id);
        StudentProfile lProfile = lStudent.getProfile();
        if (lProfile == null) {
            throw new ResourceNotFoundException(PROFILE_NOT_FOUND_MESSAGE + id);
        }
        return lProfile;
    } // end getProfileByStudentId

    /**
     * Finds every student who has the given major.
     *
     * @param major String the major to search for.
     * @return List&lt;Student&gt; the matching students, or an empty list when none match.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Student> getStudentsByMajor(String major) {
        return gStudentRepository.findByMajor(major);
    } // end getStudentsByMajor

    /**
     * Finds every student who enrolled in the given year.
     *
     * @param year Integer the enrollment year to search for.
     * @return List&lt;Student&gt; the matching students, or an empty list when none match.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Student> getStudentsByEnrollmentYear(Integer year) {
        return gStudentRepository.findByEnrollmentYear(year);
    } // end getStudentsByEnrollmentYear
} // end StudentServiceImpl
