/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.studentprofileservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.bellevue.cis530.studentprofileservice.entity.StudentProfile;

/**
 * Repository that provides database access for StudentProfile entities.
 */
public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {

    /**
     * Finds the profile that belongs to one student.
     *
     * @param studentId Long the id of the student in the Student Service.
     * @return Optional&lt;StudentProfile&gt; the matching profile, or an empty Optional when none exists.
     */
    Optional<StudentProfile> findByStudentId(Long studentId);
} // end StudentProfileRepository
