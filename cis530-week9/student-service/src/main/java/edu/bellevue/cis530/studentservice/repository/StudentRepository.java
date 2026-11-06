/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.studentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.bellevue.cis530.studentservice.entity.Student;

/**
 * Repository that provides database access for Student entities.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
} // end StudentRepository
