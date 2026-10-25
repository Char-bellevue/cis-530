/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week7;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository that provides database access for Student entities.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * Finds every student who has the given major.
     *
     * @param major String the major to search for.
     * @return List&lt;Student&gt; the matching students, or an empty list when none match.
     */
    List<Student> findByMajor(String major);

    /**
     * Finds every student who enrolled in the given year.
     *
     * @param enrollmentYear Integer the enrollment year to search for.
     * @return List&lt;Student&gt; the matching students, or an empty list when none match.
     */
    List<Student> findByEnrollmentYear(Integer enrollmentYear);
} // end StudentRepository
