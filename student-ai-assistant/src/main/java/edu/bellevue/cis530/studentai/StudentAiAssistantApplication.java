/*
Bellevue University. (2026). CIS 530 Server-Side Development, Assignment 10.2: Build a Spring AI Student Support Assistant. Bellevue University, all rights reserved.
Modified by C. Natarajan 2026
*/
package edu.bellevue.cis530.studentai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This class is the main entry point of the Student AI Assistant project.
 * It starts the Spring application context and the embedded web server.
 */
@SpringBootApplication
public class StudentAiAssistantApplication {

    /**
     * Starts the Spring Boot application.
     *
     * @param args String[] the command-line arguments passed to the application.
     * @return nothing; the embedded server starts and listens on port 8080.
     */
    public static void main(String[] args) {
        SpringApplication.run(StudentAiAssistantApplication.class, args); // Launch the Spring context.
    } // end main
} // end StudentAiAssistantApplication