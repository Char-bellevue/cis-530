/*
Bellevue University. (2026). CIS 530 Server-Side Development, Assignment 10.2: Build a Spring AI Student Support Assistant. Bellevue University, all rights reserved.
Modified by C. Natarajan 2026
*/
package edu.bellevue.cis530.studentai.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class carries the four input fields for the /api/ai/study-plan endpoint.
 * Lombok generates the getters, setters, and constructors.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyPlanRequest {

    @NotBlank(message = "studentName is required")
    private String studentName; // The name of the student who needs the plan.

    @NotBlank(message = "topic is required")
    private String topic; // The subject the student is studying.

    @NotBlank(message = "problem is required")
    private String problem; // What the student is struggling to understand.

    @NotBlank(message = "goal is required")
    private String goal; // What the student wants to achieve.
} // end StudyPlanRequest
