/*
Bellevue University. (2026). CIS 530 Server-Side Development, Assignment 10.2: Build a Spring AI Student Support Assistant. Bellevue University, all rights reserved.
Modified by C. Natarajan 2026
*/
package edu.bellevue.cis530.studentai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class is the consistent JSON response returned by the text-based endpoints.
 * Lombok generates the getters, setters, and constructors.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiTextResponse {

    private String answer; // The text returned by the model.
    private String provider; // The name of the AI provider that produced the answer.
} // end AiTextResponse
