/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the CIS 530 Week 5 Spring Boot application.
 */
@SpringBootApplication
public class Cis530Week5Application {

    /**
     * Starts the Spring Boot application.
     *
     * @param args String[] the command-line arguments passed to the application.
     * @return nothing; the embedded web server starts and keeps running.
     */
    public static void main(String[] args) {
        SpringApplication.run(Cis530Week5Application.class, args); // Launch the embedded server and Spring context.
    } // end main
} // end Cis530Week5Application
