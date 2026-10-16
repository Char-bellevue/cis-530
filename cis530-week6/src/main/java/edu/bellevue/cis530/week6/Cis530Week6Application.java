/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the CIS 530 Week 6 Spring Boot REST client application.
 */
@SpringBootApplication
public class Cis530Week6Application {

    /**
     * Starts the Spring Boot application on the port set in application.properties.
     * @param args String[] command-line arguments passed to the application.
     * @return nothing; the embedded server starts and waits for requests.
     */
    public static void main(String[] args) {
        SpringApplication.run(Cis530Week6Application.class, args); // Boot the Spring context.
    } // end main
} // end Cis530Week6Application
