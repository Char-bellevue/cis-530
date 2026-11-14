/*
Bellevue University. (2026). CIS 530 Server-Side Development, Assignment 10.2: Build a Spring AI Student Support Assistant. Bellevue University, all rights reserved.
Modified by C. Natarajan 2026
*/
package edu.bellevue.cis530.studentai.service;

import edu.bellevue.cis530.studentai.dto.AiQuestionRequest;
import edu.bellevue.cis530.studentai.dto.AiTextResponse;
import edu.bellevue.cis530.studentai.dto.ContextHelpRequest;
import edu.bellevue.cis530.studentai.dto.RagHelpRequest;
import edu.bellevue.cis530.studentai.dto.StudyPlanRequest;
import edu.bellevue.cis530.studentai.dto.StudyPlanResponse;

/**
 * This interface defines the four AI operations that the controller can call.
 */
public interface AiService {

    /**
     * Answers a plain question with a basic prompt.
     *
     * @param request AiQuestionRequest the question from the student.
     * @return AiTextResponse the answer and the provider name.
     */
    AiTextResponse askQuestion(AiQuestionRequest request);

    /**
     * Builds a structured study plan for a student.
     *
     * @param request StudyPlanRequest the student name, topic, problem, and goal.
     * @return StudyPlanResponse the summary, action items, important terms, and encouragement.
     */
    StudyPlanResponse createStudyPlan(StudyPlanRequest request);

    /**
     * Answers a question by stuffing the local course notes into the prompt.
     *
     * @param request ContextHelpRequest the question from the student.
     * @return AiTextResponse the answer and the provider name.
     */
    AiTextResponse contextHelp(ContextHelpRequest request);

    /**
     * Answers a question by retrieving related documents from the vector store.
     *
     * @param request RagHelpRequest the question from the student.
     * @return AiTextResponse the answer and the provider name.
     */
    AiTextResponse ragHelp(RagHelpRequest request);
} // end AiService
