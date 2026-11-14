/*
Bellevue University. (2026). CIS 530 Server-Side Development, Assignment 10.2: Build a Spring AI Student Support Assistant. Bellevue University, all rights reserved.
Modified by C. Natarajan 2026
*/
package edu.bellevue.cis530.studentai.service.impl;

import edu.bellevue.cis530.studentai.dto.AiQuestionRequest;
import edu.bellevue.cis530.studentai.dto.AiTextResponse;
import edu.bellevue.cis530.studentai.dto.ContextHelpRequest;
import edu.bellevue.cis530.studentai.dto.RagHelpRequest;
import edu.bellevue.cis530.studentai.dto.StudyPlanRequest;
import edu.bellevue.cis530.studentai.dto.StudyPlanResponse;
import edu.bellevue.cis530.studentai.service.AiService;
import edu.bellevue.cis530.studentai.service.DocumentLoaderService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

/**
 * This is the main business-logic class.
 * The controller receives HTTP requests, but this service does the actual AI work.
 */
@Service
public class AiAssistantService implements AiService {

    private final ChatClient chatClient; // Sends prompts to the OpenAI chat model.
    private final VectorStore vectorStore; // Holds the embedded documents used for RAG.
    private final DocumentLoaderService documentLoaderService; // Reads the local note files.

    /**
     * Constructor injection gives this service access to Spring AI and the local documents.
     *
     * @param chatClient ChatClient the client used to call the chat model.
     * @param vectorStore VectorStore the store searched by the RAG advisor.
     * @param documentLoaderService DocumentLoaderService the service that reads the local files.
     */
    public AiAssistantService(ChatClient chatClient,
                              VectorStore vectorStore,
                              DocumentLoaderService documentLoaderService) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
        this.documentLoaderService = documentLoaderService;
    } // end AiAssistantService

    /**
     * Endpoint logic for /api/ai/ask.
     * This method shows the simplest use of Spring AI: send a prompt and return text.
     *
     * @param request AiQuestionRequest the question from the student.
     * @return AiTextResponse the model answer and the provider name.
     */
    @Override
    public AiTextResponse askQuestion(AiQuestionRequest request) {
        String answer = chatClient.prompt()
                .user(request.getQuestion()) // The student question becomes the user message.
                .call()
                .content(); // Return the reply as plain text.
        return new AiTextResponse(answer, "openai");
    } // end askQuestion

    /**
     * Endpoint logic for /api/ai/study-plan.
     * This method asks the model for structured data and maps the result into a DTO.
     *
     * @param request StudyPlanRequest the student name, topic, problem, and goal.
     * @return StudyPlanResponse the structured study plan mapped from the model response.
     */
    @Override
    public StudyPlanResponse createStudyPlan(StudyPlanRequest request) {
        return chatClient.prompt()
                .system("""
                        Return a study plan as a Java-friendly structured response.
                        Include these fields only:
                        summary,
                        actionItems,
                        importantTerms,
                        encouragement.
                        actionItems should contain exactly 3 items.
                        importantTerms should contain 4 or 5 short terms.
                        """)
                .user(user -> user.text("""
                                Student name: {studentName}
                                Topic: {topic}
                                Problem: {problem}
                                Goal: {goal}
                                Create a short study plan for this student.
                                """)
                        .param("studentName", request.getStudentName())
                        .param("topic", request.getTopic())
                        .param("problem", request.getProblem())
                        .param("goal", request.getGoal()))
                .call()
                .entity(StudyPlanResponse.class); // Map the model JSON into the DTO.
    } // end createStudyPlan

    /**
     * Endpoint logic for /api/ai/context-help.
     * This method demonstrates "bring your own data" by stuffing local notes into the prompt.
     *
     * @param request ContextHelpRequest the question from the student.
     * @return AiTextResponse the model answer and the provider name.
     */
    @Override
    public AiTextResponse contextHelp(ContextHelpRequest request) {
        String notes = documentLoaderService.loadWeek10Notes(); // Read the notes file once per request.
        String answer = chatClient.prompt()
                .system("Answer using the provided course notes first. If the notes are not enough, say that clearly.")
                .user(user -> user.text("""
                                Course notes:
                                {notes}

                                Student question:
                                {question}
                                """)
                        .param("notes", notes)
                        .param("question", request.getQuestion()))
                .call()
                .content();
        return new AiTextResponse(answer, "openai");
    } // end contextHelp

    /**
     * Endpoint logic for /api/ai/rag-help.
     * This method demonstrates introductory RAG with a vector store and an advisor.
     *
     * @param request RagHelpRequest the question from the student.
     * @return AiTextResponse the model answer grounded in retrieved documents, and the provider name.
     */
    @Override
    public AiTextResponse ragHelp(RagHelpRequest request) {
        String answer = chatClient.prompt()
                .advisors(QuestionAnswerAdvisor.builder(vectorStore).build()) // Retrieve related documents first.
                .user(request.getQuestion())
                .call()
                .content();
        return new AiTextResponse(answer, "openai");
    } // end ragHelp
} // end AiAssistantService
