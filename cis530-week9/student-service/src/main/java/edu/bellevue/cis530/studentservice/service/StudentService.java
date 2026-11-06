/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.studentservice.service;

import java.util.List;

import edu.bellevue.cis530.studentservice.entity.Student;

/**
 * Service contract that holds the business logic for students.
 */
public interface StudentService {

    /**
     * Creates a new student.
     *
     * @param student Student the validated student to save.
     * @return Student the saved student with the generated id.
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
     * Deletes a student.
     *
     * @param id Long the id of the student to delete.
     * @return nothing; the student is removed.
     */
    void deleteStudent(Long id);
} // end StudentService
