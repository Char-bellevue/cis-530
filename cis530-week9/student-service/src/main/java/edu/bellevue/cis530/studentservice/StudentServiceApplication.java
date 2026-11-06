/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.studentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Entry point of the Student Service microservice.
 * It enables Feign clients so that this service can call the Student Profile Service by name.
 */
@SpringBootApplication
@EnableFeignClients
public class StudentServiceApplication {

    /**
     * Starts the Student Service Spring Boot application.
     *
     * @param args String[] the command-line arguments passed to the application.
     * @return nothing; the embedded web server starts on port 8081.
     */
    public static void main(String[] args) {
        SpringApplication.run(StudentServiceApplication.class, args); // Boot the application context.
    } // end main
} // end StudentServiceApplication
