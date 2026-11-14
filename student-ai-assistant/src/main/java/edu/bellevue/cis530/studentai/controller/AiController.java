/*
Bellevue University. (2026). CIS 530 Server-Side Development, Assignment 10.2: Build a Spring AI Student Support Assistant. Bellevue University, all rights reserved.
Modified by C. Natarajan 2026
*/
package edu.bellevue.cis530.studentai.controller;

import edu.bellevue.cis530.studentai.dto.AiQuestionRequest;
import edu.bellevue.cis530.studentai.dto.AiTextResponse;
import edu.bellevue.cis530.studentai.dto.ContextHelpRequest;
import edu.bellevue.cis530.studentai.dto.RagHelpRequest;
import edu.bellevue.cis530.studentai.dto.StudyPlanRequest;
import edu.bellevue.cis530.studentai.dto.StudyPlanResponse;
import edu.bellevue.cis530.studentai.service.AiService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller exposes the REST endpoints required by the assignment.
 * Each method matches one endpoint from the Week 10 instructions.
 */
@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiAssistantService; // The service layer that performs the AI work.

    /**
     * Constructor injection gives this controller access to the service layer.
     *
     * @param aiAssistantService AiService the service that handles every AI request.
     */
    public AiController(AiService aiAssistantService) {
        this.aiAssistantService = aiAssistantService;
    } // end AiController

    /**
     * Endpoint 1: plain prompt-based question answering.
     *
     * @param request AiQuestionRequest the validated question from the request body.
     * @return ResponseEntity<AiTextResponse> an HTTP 200 response holding the answer.
     */
    @PostMapping("/ask")
    public ResponseEntity<AiTextResponse> ask(@Valid @RequestBody AiQuestionRequest request) {
        return ResponseEntity.ok(aiAssistantService.askQuestion(request));
    } // end ask

    /**
     * Endpoint 2: structured output mapped into a Java DTO.
     *
     * @param request StudyPlanRequest the validated student details from the request body.
     * @return ResponseEntity<StudyPlanResponse> an HTTP 200 response holding the study plan.
     */
    @PostMapping("/study-plan")
    public ResponseEntity<StudyPlanResponse> studyPlan(@Valid @RequestBody StudyPlanRequest request) {
        return ResponseEntity.ok(aiAssistantService.createStudyPlan(request));
    } // end studyPlan

    /**
     * Endpoint 3: bring-your-own-data by manually inserting notes into the prompt.
     *
     * @param request ContextHelpRequest the validated question from the request body.
     * @return ResponseEntity<AiTextResponse> an HTTP 200 response holding the answer.
     */
    @PostMapping("/context-help")
    public ResponseEntity<AiTextResponse> contextHelp(@Valid @RequestBody ContextHelpRequest request) {
        return ResponseEntity.ok(aiAssistantService.contextHelp(request));
    } // end contextHelp

    /**
     * Endpoint 4: RAG using a vector store and QuestionAnswerAdvisor.
     *
     * @param request RagHelpRequest the validated question from the request body.
     * @return ResponseEntity<AiTextResponse> an HTTP 200 response holding the answer.
     */
    @PostMapping("/rag-help")
    public ResponseEntity<AiTextResponse> ragHelp(@Valid @RequestBody RagHelpRequest request) {
        return ResponseEntity.ok(aiAssistantService.ragHelp(request));
    } // end ragHelp
} // end AiController
