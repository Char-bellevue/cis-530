/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of the API Gateway, the single public entry point that routes requests to the microservices.
 */
@SpringBootApplication
public class ApiGatewayApplication {

    /**
     * Starts the API Gateway Spring Boot application.
     *
     * @param args String[] the command-line arguments passed to the application.
     * @return nothing; the gateway starts on port 8080 and loads its routes from application.properties.
     */
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args); // Boot the application context.
    } // end main
} // end ApiGatewayApplication
