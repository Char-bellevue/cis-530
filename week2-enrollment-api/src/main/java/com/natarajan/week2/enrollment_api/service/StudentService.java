/*
 Natarajan, C. (2026). CIS530-T301 Server-Side Development (2271-1). Bellevue University.
*/
package com.natarajan.week2.enrollment_api.service;

import com.natarajan.week2.enrollment_api.exception.StudentNotFoundException;
import com.natarajan.week2.enrollment_api.model.Student;
import com.natarajan.week2.enrollment_api.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository gStudentRepository;

    /**
     * Creates the service with its required repository dependency.
     * @param studentRepository StudentRepository used for data access.
     * @return No value; the constructor stores the repository reference.
     */
    public StudentService(StudentRepository studentRepository) {
        this.gStudentRepository = studentRepository;
    } // end StudentService

    /**
     * Returns every student currently stored.
     * @return List<Student> containing all students.
     */
    public List<Student> getAllStudents() {
        return gStudentRepository.findAll();
    } // end getAllStudents

    /**
     * Finds one student by identifier.
     * @param id Long student identifier to search for.
     * @return Student matching the given identifier.
     */
    public Student getStudentById(Long id) {
        return gStudentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student with id " + id + " not found"));
    } // end getStudentById

    /**
     * Creates a new student record.
     * @param student Student object to store.
     * @return Student containing the saved student with its assigned identifier.
     */
    public Student createStudent(Student student) {
        return gStudentRepository.save(student);
    } // end createStudent

    /**
     * Updates an existing student's data.
     * @param id Long student identifier to update.
     * @param student Student object containing replacement values.
     * @return Student containing the updated student.
     */
    public Student updateStudent(Long id, Student student) {
        return gStudentRepository.update(id, student)
                .orElseThrow(() -> new StudentNotFoundException("Student with id " + id + " not found"));
    } // end updateStudent

    /**
     * Deletes a student by identifier.
     * @param id Long student identifier to delete.
     * @return No value; throws StudentNotFoundException if the student does not exist.
     */
    public void deleteStudent(Long id) {
        boolean lWasDeleted = gStudentRepository.delete(id);
        if (!lWasDeleted) {
            throw new StudentNotFoundException("Student with id " + id + " not found");
        }
    } // end deleteStudent
} // end StudentService
