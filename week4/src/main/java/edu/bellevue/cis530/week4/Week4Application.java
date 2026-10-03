/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point class that starts the Student Management Spring Boot application.
 */
@SpringBootApplication
public class Week4Application {

    /**
     * Starts the embedded web server and the Spring application context.
     * @param args String[] optional command-line arguments passed to Spring Boot
     * @return none, the application keeps running until it is stopped.
     */
    public static void main(String[] args) {
        SpringApplication.run(Week4Application.class, args); // Launch the Spring Boot application.
    } // end main
} // end Week4Application
