/*
Bellevue University. (2026). CIS 530 Server-Side Development, Assignment 10.2: Build a Spring AI Student Support Assistant. Bellevue University, all rights reserved.
Modified by C. Natarajan 2026
*/
package edu.bellevue.cis530.studentai.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class is the structured output that the model response is mapped into.
 * Lombok generates the getters, setters, and constructors.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"summary", "actionItems", "importantTerms", "encouragement"})
public class StudyPlanResponse {

    private String summary; // A short overview of the study plan.
    private List<String> actionItems; // Exactly three steps the student should take.
    private List<String> importantTerms; // Four or five key terms to learn.
    private String encouragement; // A short motivating message.
} // end StudyPlanResponse
