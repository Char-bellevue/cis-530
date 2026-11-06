/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.studentservice.dto;

import java.math.BigDecimal;

import lombok.Data;

/**
 * Data contract that represents profile data returned by the Student Profile Service.
 * It keeps the Student Service independent from the internal entity of the other service.
 */
@Data
public class StudentProfileDto {

    // The primary key of the profile in the Student Profile Service database.
    private Long id;

    // The logical reference to the id of the matching student in student_db.
    private Long studentId;

    // The major of the student.
    private String major;

    // The grade point average of the student.
    private BigDecimal gpa;

    // The short biography of the student.
    private String bio;
} // end StudentProfileDto
