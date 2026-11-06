/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.studentservice.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.bellevue.cis530.studentservice.entity.Student;
import edu.bellevue.cis530.studentservice.exception.ResourceNotFoundException;
import edu.bellevue.cis530.studentservice.repository.StudentRepository;
import edu.bellevue.cis530.studentservice.service.StudentService;
import lombok.RequiredArgsConstructor;

/**
 * Default implementation of the student service that uses the student repository.
 */
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    // The start of the message used when a student cannot be found.
    private static final String STUDENT_NOT_FOUND_MESSAGE = "Student not found with id: ";

    // The repository used to read and write students; Lombok injects it through the constructor.
    private final StudentRepository gStudentRepository;

    /**
     * Creates a new student.
     *
     * @param student Student the validated student to save.
     * @return Student the saved student with the generated id.
     */
    @Override
    public Student createStudent(Student student) {
        return gStudentRepository.save(student); // Insert the row and return the saved entity.
    } // end createStudent

    /**
     * Gets every student in the database.
     *
     * @return List&lt;Student&gt; all students, or an empty list when none exist.
     */
    @Override
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
    public Student getStudentById(Long id) {
        return gStudentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(STUDENT_NOT_FOUND_MESSAGE + id)); // Missing id gives a 404.
    } // end getStudentById

    /**
     * Deletes a student.
     *
     * @param id Long the id of the student to delete.
     * @return nothing; the student is removed.
     */
    @Override
    public void deleteStudent(Long id) {
        gStudentRepository.deleteById(id); // Remove the row that has the given id.
    } // end deleteStudent
} // end StudentServiceImpl
