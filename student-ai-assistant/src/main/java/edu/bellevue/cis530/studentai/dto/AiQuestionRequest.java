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
 * This class carries the single question sent to the /api/ai/ask endpoint.
 * Lombok generates the getters, setters, and constructors.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiQuestionRequest {

    @NotBlank(message = "question is required")
    private String question; // The text the student wants answered.
} // end AiQuestionRequest
