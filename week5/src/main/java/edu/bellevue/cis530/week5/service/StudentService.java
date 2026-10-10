/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week5.service;

import java.util.List;

import edu.bellevue.cis530.week5.entity.Student;
import edu.bellevue.cis530.week5.entity.StudentProfile;

/**
 * Service contract that holds the business logic for students and their profiles.
 */
public interface StudentService {

    /**
     * Creates a new student together with its profile.
     *
     * @param student Student the validated student and profile to save.
     * @return Student the saved student with generated ids.
     */
    Student createStudent(Student student);

    /**
     * Gets every student in the database.
     *
     * @return List&lt;Student&gt; all students, or an empty list when none exist.
     */
    List<Student> getAllStudents();

    /**
     * Gets one student by id.
     *
     * @param id Long the id of the student to find.
     * @return Student the student that has the given id.
     */
    Student getStudentById(Long id);

    /**
     * Updates an existing student together with its profile.
     *
     * @param id Long the id of the student to update.
     * @param student Student the validated new student and profile values.
     * @return Student the updated student.
     */
    Student updateStudent(Long id, Student student);

    /**
     * Deletes a student together with its profile.
     *
     * @param id Long the id of the student to delete.
     * @return nothing; the student and profile are removed.
     */
    void deleteStudent(Long id);

    /**
     * Gets the profile that belongs to one student.
     *
     * @param id Long the id of the student whose profile is wanted.
     * @return StudentProfile the profile of the student.
     */
    StudentProfile getProfileByStudentId(Long id);

    /**
     * Finds every student who has the given major.
     *
     * @param major String the major to search for.
     * @return List&lt;Student&gt; the matching students, or an empty list when none match.
     */
    List<Student> getStudentsByMajor(String major);

    /**
     * Finds every student who enrolled in the given year.
     *
     * @param year Integer the enrollment year to search for.
     * @return List&lt;Student&gt; the matching students, or an empty list when none match.
     */
    List<Student> getStudentsByEnrollmentYear(Integer year);
} // end StudentService
