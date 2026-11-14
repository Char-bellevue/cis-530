/*
Bellevue University. (2026). CIS 530 Server-Side Development, Assignment 10.2: Build a Spring AI Student Support Assistant. Bellevue University, all rights reserved.
Modified by C. Natarajan 2026
*/
package edu.bellevue.cis530.studentai.config;

import edu.bellevue.cis530.studentai.service.DocumentLoaderService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class holds the beans that the rest of the project needs.
 * The beans are kept here so the controller and service classes stay clean.
 */
@Configuration
public class StudentAiConfig {

    /**
     * Creates the ChatClient bean because all four endpoints send prompts to the model.
     * The bean also gives every request a helpful default system instruction.
     *
     * @param builder ChatClient.Builder the auto-configured builder supplied by Spring AI.
     * @return ChatClient a client that already includes the default system instruction.
     */
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem("You are a friendly teaching assistant for university students "
                        + "who are learning Spring Boot and Spring AI. "
                        + "Give clear, accurate, and concise answers.") // Default instruction for every prompt.
                .build();
    } // end chatClient

    /**
     * Creates the VectorStore bean for the RAG endpoint.
     * SimpleVectorStore is a small in-memory store that works well for classroom demos.
     *
     * @param embeddingModel EmbeddingModel the model that turns text into embedding vectors.
     * @return VectorStore an empty in-memory vector store backed by the embedding model.
     */
    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {
        return SimpleVectorStore.builder(embeddingModel).build();
    } // end vectorStore

    /**
     * Creates a runner that loads the local text files into the vector store when the project starts.
     * The API-key check keeps startup friendlier for students. If the key is missing,
     * the application can still start so students can see the project structure first.
     *
     * @param vectorStore VectorStore the store that receives the documents.
     * @param documentLoaderService DocumentLoaderService the service that reads the local text files.
     * @param apiKey String the OpenAI API key read from the application properties.
     * @return ApplicationRunner a runner that loads the documents once at startup.
     */
    @Bean
    public ApplicationRunner loadVectorStore(
            VectorStore vectorStore,
            DocumentLoaderService documentLoaderService,
            @Value("${spring.ai.openai.api-key:}") String apiKey) {
        return args -> {
            // Skip loading when the key is missing, because embedding the documents would fail.
            if (apiKey == null || apiKey.isBlank() || apiKey.contains("${")) {
                System.out.println("Skipping vector-store loading because the OpenAI API key is missing or unresolved.");
                return;
            }
            vectorStore.add(documentLoaderService.loadRagDocuments()); // Embed and store every document.
            System.out.println("RAG documents loaded into the vector store.");
        };
    } // end loadVectorStore
} // end StudentAiConfig
