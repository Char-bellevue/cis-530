/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week5.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.bellevue.cis530.week5.entity.StudentProfile;

/**
 * Repository that provides database access for StudentProfile entities.
 */
public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {
} // end StudentProfileRepository
