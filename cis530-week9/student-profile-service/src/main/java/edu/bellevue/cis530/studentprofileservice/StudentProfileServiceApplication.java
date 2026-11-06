/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.studentprofileservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of the Student Profile Service microservice, which owns the student_profile_db database.
 */
@SpringBootApplication
public class StudentProfileServiceApplication {

    /**
     * Starts the Student Profile Service Spring Boot application.
     *
     * @param args String[] the command-line arguments passed to the application.
     * @return nothing; the embedded web server starts on port 8082.
     */
    public static void main(String[] args) {
        SpringApplication.run(StudentProfileServiceApplication.class, args); // Boot the application context.
    } // end main
} // end StudentProfileServiceApplication
