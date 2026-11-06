/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.studentservice.dto;

import edu.bellevue.cis530.studentservice.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Combines the student from this service with the profile from the Student Profile Service
 * so that a client receives both in one response.
 */
@Data
@AllArgsConstructor
public class StudentWithProfileResponse {

    // The student record read from student_db.
    private Student student;

    // The profile record retrieved from the Student Profile Service.
    private StudentProfileDto profile;
} // end StudentWithProfileResponse
