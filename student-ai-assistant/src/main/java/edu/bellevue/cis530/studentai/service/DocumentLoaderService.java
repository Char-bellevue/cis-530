/*
Bellevue University. (2026). CIS 530 Server-Side Development, Assignment 10.2: Build a Spring AI Student Support Assistant. Bellevue University, all rights reserved.
Modified by C. Natarajan 2026
*/
package edu.bellevue.cis530.studentai.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import org.springframework.ai.document.Document;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

/**
 * This service reads the local text files used by the assignment.
 * File reading is separated from AI prompting so the project is easier to understand.
 */
@Service
public class DocumentLoaderService {

    /**
     * Loads the Week 10 notes for the context-help endpoint.
     * The result is plain text that can be stuffed into the prompt.
     *
     * @return String the full text of docs/week10_notes.txt.
     */
    public String loadWeek10Notes() {
        return readFile("docs/week10_notes.txt");
    } // end loadWeek10Notes

    /**
     * Loads the documents for the RAG endpoint.
     * Each file becomes a Document object that can be added to the vector store.
     *
     * @return List<Document> one document for each of the three local text files.
     */
    public List<Document> loadRagDocuments() {
        return List.of(
                new Document(readFile("docs/week10_notes.txt"), Map.of("source", "week10_notes.txt")),
                new Document(readFile("docs/spring_ai_faq.txt"), Map.of("source", "spring_ai_faq.txt")),
                new Document(readFile("docs/rag_basics.txt"), Map.of("source", "rag_basics.txt"))
        );
    } // end loadRagDocuments

    /**
     * Helper method used by both public methods above.
     * File-reading logic is kept in one place so the code is easier to maintain.
     *
     * @param path String the classpath location of the file to read.
     * @return String the file contents decoded as UTF-8 text.
     */
    private String readFile(String path) {
        try {
            ClassPathResource resource = new ClassPathResource(path);
            try (var inputStream = resource.getInputStream()) {
                return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Could not read resource file: " + path, exception);
        }
    } // end readFile
} // end DocumentLoaderService
