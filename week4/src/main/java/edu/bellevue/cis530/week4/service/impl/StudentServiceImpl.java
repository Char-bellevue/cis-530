/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week4.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import edu.bellevue.cis530.week4.entity.Student;
import edu.bellevue.cis530.week4.exception.ResourceNotFoundException;
import edu.bellevue.cis530.week4.repository.StudentRepository;
import edu.bellevue.cis530.week4.service.StudentService;

/**
 * Service implementation that holds the business logic for student records.
 */
@Service
public class StudentServiceImpl implements StudentService {

    // Repository used for every database operation in this class.
    private final StudentRepository gStudentRepository;

    /**
     * Creates the service and receives the repository through constructor injection.
     * @param studentRepository StudentRepository the repository that Spring provides
     * @return none, a new service object is created.
     */
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.gStudentRepository = studentRepository; // Keep the injected repository.
    } // end StudentServiceImpl

    /**
     * Creates a new student record after checking the required fields.
     * @param student Student the student data to save
     * @return Student the saved student, including the generated ID.
     */
    @Override
    public Student createStudent(Student student) {
        validateRequiredFields(student); // Reject missing required fields.
        student.setId(null); // Force a new record even if the client sent an ID.
        return gStudentRepository.save(student);
    } // end createStudent

    /**
     * Retrieves one student by ID.
     * @param id Long the ID of the student
     * @return Student the matching student.
     */
    @Override
    public Student getStudentById(Long id) {
        return findStudentOrThrow(id);
    } // end getStudentById

    /**
     * Retrieves every student.
     * @return List<Student> all students in the database.
     */
    @Override
    public List<Student> getAllStudents() {
        return gStudentRepository.findAll();
    } // end getAllStudents

    /**
     * Updates an existing student after checking the required fields.
     * @param id Long the ID of the student to update
     * @param student Student the new student data
     * @return Student the updated student.
     */
    @Override
    public Student updateStudent(Long id, Student student) {
        validateRequiredFields(student); // Reject missing required fields.
        Student lExistingStudent = findStudentOrThrow(id); // Throws a 404 error if the ID is missing.
        lExistingStudent.setFirstName(student.getFirstName());
        lExistingStudent.setLastName(student.getLastName());
        lExistingStudent.setEmail(student.getEmail());
        lExistingStudent.setMajor(student.getMajor());
        lExistingStudent.setGpa(student.getGpa());
        lExistingStudent.setEnrollmentYear(student.getEnrollmentYear());
        return gStudentRepository.save(lExistingStudent);
    } // end updateStudent

    /**
     * Deletes a student by ID.
     * @param id Long the ID of the student to delete
     * @return none, the student is removed from the database.
     */
    @Override
    public void deleteStudentById(Long id) {
        Student lExistingStudent = findStudentOrThrow(id); // Throws a 404 error if the ID is missing.
        gStudentRepository.delete(lExistingStudent);
    } // end deleteStudentById

    /**
     * Retrieves students in a given major.
     * @param major String the major to search for
     * @return List<Student> every student in that major.
     */
    @Override
    public List<Student> getByMajor(String major) {
        return gStudentRepository.findByMajor(major);
    } // end getByMajor

    /**
     * Retrieves students with a GPA above a given value.
     * @param gpa Double the GPA that results must exceed
     * @return List<Student> every student with a higher GPA.
     */
    @Override
    public List<Student> getByGpaGreaterThan(Double gpa) {
        return gStudentRepository.findByGpaGreaterThan(gpa);
    } // end getByGpaGreaterThan

    /**
     * Retrieves students enrolled after a given year.
     * @param year Integer the year that results must exceed
     * @return List<Student> every student enrolled after that year.
     */
    @Override
    public List<Student> getByEnrollmentYearGreaterThan(Integer year) {
        return gStudentRepository.findByEnrollmentYearGreaterThan(year);
    } // end getByEnrollmentYearGreaterThan

    /**
     * Retrieves the three students with the highest GPA.
     * @return List<Student> up to three students ordered from highest GPA to lowest.
     */
    @Override
    public List<Student> getTop3ByGpa() {
        return gStudentRepository.findTop3ByOrderByGpaDesc();
    } // end getTop3ByGpa

    /**
     * Retrieves all students sorted by last name.
     * @param direction String either asc or desc
     * @return List<Student> every student sorted by last name.
     */
    @Override
    public List<Student> getAllSortedByLastName(String direction) {
        // Throws an IllegalArgumentException when the direction is not asc or desc.
        Sort.Direction lDirection = Sort.Direction.fromString(direction);
        return gStudentRepository.findAll(Sort.by(lDirection, "lastName"));
    } // end getAllSortedByLastName

    /**
     * Retrieves one page of students.
     * @param page int the zero-based page number
     * @param size int the number of students per page
     * @return Page<Student> the requested page of students.
     */
    @Override
    public Page<Student> getStudentsPaged(int page, int size) {
        // Throws an IllegalArgumentException when the page is negative or the size is less than one.
        Pageable lPageable = PageRequest.of(page, size);
        return gStudentRepository.findAll(lPageable);
    } // end getStudentsPaged

    /**
     * Retrieves students in a given major using a custom JPQL query.
     * @param major String the major to search for
     * @return List<Student> every student in that major.
     */
    @Override
    public List<Student> getByMajorJpql(String major) {
        return gStudentRepository.findStudentsByMajorJpql(major);
    } // end getByMajorJpql

    /**
     * Deletes students enrolled in a given year using a custom JPQL query.
     * @param year Integer the enrollment year whose students are deleted
     * @return int the number of students that were deleted.
     */
    @Override
    public int deleteByEnrollmentYearJpql(Integer year) {
        return gStudentRepository.deleteStudentsByEnrollmentYearJpql(year);
    } // end deleteByEnrollmentYearJpql

    /**
     * Finds a student by ID or throws an exception when the ID does not exist.
     * @param id Long the ID of the student
     * @return Student the matching student.
     */
    private Student findStudentOrThrow(Long id) {
        return gStudentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    } // end findStudentOrThrow

    /**
     * Checks that every required student field has a value.
     * @param student Student the student data to check
     * @return none, an IllegalArgumentException is thrown when a field is missing.
     */
    private void validateRequiredFields(Student student) {
        if (student.getFirstName() == null || student.getFirstName().isBlank()) {
            throw new IllegalArgumentException("firstName is required.");
        }
        if (student.getLastName() == null || student.getLastName().isBlank()) {
            throw new IllegalArgumentException("lastName is required.");
        }
        if (student.getEmail() == null || student.getEmail().isBlank()) {
            throw new IllegalArgumentException("email is required.");
        }
        if (student.getMajor() == null || student.getMajor().isBlank()) {
            throw new IllegalArgumentException("major is required.");
        }
        if (student.getGpa() == null) {
            throw new IllegalArgumentException("gpa is required.");
        }
        if (student.getEnrollmentYear() == null) {
            throw new IllegalArgumentException("enrollmentYear is required.");
        }
    } // end validateRequiredFields
} // end StudentServiceImpl
