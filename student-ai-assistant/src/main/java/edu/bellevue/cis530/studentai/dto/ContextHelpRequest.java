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
 * This class carries the question sent to the /api/ai/context-help endpoint.
 * Lombok generates the getters, setters, and constructors.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContextHelpRequest {

    @NotBlank(message = "question is required")
    private String question; // The question answered with the stuffed course notes.
} // end ContextHelpRequest
