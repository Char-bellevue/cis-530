/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week4.service;

import java.util.List;

import org.springframework.data.domain.Page;

import edu.bellevue.cis530.week4.entity.Student;

/**
 * Service interface that defines the business operations for student records.
 */
public interface StudentService {

    /**
     * Creates a new student record.
     * @param student Student the student data to save
     * @return Student the saved student, including the generated ID.
     */
    Student createStudent(Student student);

    /**
     * Retrieves one student by ID.
     * @param id Long the ID of the student
     * @return Student the matching student.
     */
    Student getStudentById(Long id);

    /**
     * Retrieves every student.
     * @return List<Student> all students in the database.
     */
    List<Student> getAllStudents();

    /**
     * Updates an existing student.
     * @param id Long the ID of the student to update
     * @param student Student the new student data
     * @return Student the updated student.
     */
    Student updateStudent(Long id, Student student);

    /**
     * Deletes a student by ID.
     * @param id Long the ID of the student to delete
     * @return none, the student is removed from the database.
     */
    void deleteStudentById(Long id);

    /**
     * Retrieves students in a given major.
     * @param major String the major to search for
     * @return List<Student> every student in that major.
     */
    List<Student> getByMajor(String major);

    /**
     * Retrieves students with a GPA above a given value.
     * @param gpa Double the GPA that results must exceed
     * @return List<Student> every student with a higher GPA.
     */
    List<Student> getByGpaGreaterThan(Double gpa);

    /**
     * Retrieves students enrolled after a given year.
     * @param year Integer the year that results must exceed
     * @return List<Student> every student enrolled after that year.
     */
    List<Student> getByEnrollmentYearGreaterThan(Integer year);

    /**
     * Retrieves the three students with the highest GPA.
     * @return List<Student> up to three students ordered from highest GPA to lowest.
     */
    List<Student> getTop3ByGpa();

    /**
     * Retrieves all students sorted by last name.
     * @param direction String either asc or desc
     * @return List<Student> every student sorted by last name.
     */
    List<Student> getAllSortedByLastName(String direction);

    /**
     * Retrieves one page of students.
     * @param page int the zero-based page number
     * @param size int the number of students per page
     * @return Page<Student> the requested page of students.
     */
    Page<Student> getStudentsPaged(int page, int size);

    /**
     * Retrieves students in a given major using a custom JPQL query.
     * @param major String the major to search for
     * @return List<Student> every student in that major.
     */
    List<Student> getByMajorJpql(String major);

    /**
     * Deletes students enrolled in a given year using a custom JPQL query.
     * @param year Integer the enrollment year whose students are deleted
     * @return int the number of students that were deleted.
     */
    int deleteByEnrollmentYearJpql(Integer year);
} // end StudentService
