/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week4.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import edu.bellevue.cis530.week4.entity.Student;

/**
 * Repository interface that gives the application access to the student table.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * Derived query that finds all students in a given major.
     * @param major String the major to search for
     * @return List<Student> every student whose major matches.
     */
    List<Student> findByMajor(String major);

    /**
     * Derived query that finds all students with a GPA above a given value.
     * @param gpa Double the GPA that results must exceed
     * @return List<Student> every student whose GPA is greater than the value.
     */
    List<Student> findByGpaGreaterThan(Double gpa);

    /**
     * Derived query that finds all students enrolled after a given year.
     * @param enrollmentYear Integer the year that results must exceed
     * @return List<Student> every student enrolled after that year.
     */
    List<Student> findByEnrollmentYearGreaterThan(Integer enrollmentYear);

    /**
     * Derived query that finds the three students with the highest GPA.
     * @return List<Student> up to three students ordered from highest GPA to lowest.
     */
    List<Student> findTop3ByOrderByGpaDesc();

    /**
     * Custom JPQL query that finds all students in a given major.
     * @param major String the major to search for
     * @return List<Student> every student whose major matches.
     */
    @Query("SELECT student FROM Student student WHERE student.major = :major")
    List<Student> findStudentsByMajorJpql(@Param("major") String major);

    /**
     * Custom JPQL query that deletes all students enrolled in a given year.
     * @param enrollmentYear Integer the enrollment year whose students are deleted
     * @return int the number of student rows that were deleted.
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Student student WHERE student.enrollmentYear = :enrollmentYear")
    int deleteStudentsByEnrollmentYearJpql(@Param("enrollmentYear") Integer enrollmentYear);
} // end StudentRepository
