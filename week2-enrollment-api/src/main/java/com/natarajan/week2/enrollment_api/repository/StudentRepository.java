/*
 Natarajan, C. (2026). CIS530-T301 Server-Side Development (2271-1). Bellevue University.
*/
package com.natarajan.week2.enrollment_api.repository;

import com.natarajan.week2.enrollment_api.model.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class StudentRepository {

    private final Map<Long, Student> gStudents = new LinkedHashMap<>();
    private final AtomicLong gNextId = new AtomicLong(1);

    /**
     * Creates the repository with two sample students for local testing.
     * @return No value; the constructor seeds the in-memory data store.
     */
    public StudentRepository() {
        save(new Student(null, "John", "Doe", "john.doe@example.com", "CIS530", "Fall 2026"));
        save(new Student(null, "Jane", "Smith", "jane.smith@example.com", "CIS530", "Fall 2026"));
    } // end StudentRepository

    /**
     * Returns every student currently stored.
     * @return List<Student> containing all students.
     */
    public List<Student> findAll() {
        return new ArrayList<>(gStudents.values());
    } // end findAll

    /**
     * Finds one student by identifier.
     * @param id Long student identifier to search for.
     * @return Optional<Student> containing the student if found, otherwise empty.
     */
    public Optional<Student> findById(Long id) {
        return Optional.ofNullable(gStudents.get(id));
    } // end findById

    /**
     * Saves a new student, assigning the next available identifier if needed.
     * @param student Student object to store.
     * @return Student containing the saved student with its assigned identifier.
     */
    public Student save(Student student) {
        if (student.getId() == null) {
            student.setId(gNextId.getAndIncrement());
        }
        gStudents.put(student.getId(), student);
        return student;
    } // end save

    /**
     * Replaces an existing student's data if the identifier is present.
     * @param id Long student identifier to update.
     * @param updatedStudent Student object containing replacement values.
     * @return Optional<Student> containing the updated student if it existed, otherwise empty.
     */
    public Optional<Student> update(Long id, Student updatedStudent) {
        if (!gStudents.containsKey(id)) {
            return Optional.empty();
        }
        updatedStudent.setId(id);
        gStudents.put(id, updatedStudent);
        return Optional.of(updatedStudent);
    } // end update

    /**
     * Deletes a student by identifier.
     * @param id Long student identifier to delete.
     * @return boolean true when a student was found and removed.
     */
    public boolean delete(Long id) {
        return gStudents.remove(id) != null;
    } // end delete
} // end StudentRepository
