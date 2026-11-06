/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.serviceregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Entry point of the Eureka Service Registry, where every microservice registers by name.
 */
@SpringBootApplication
@EnableEurekaServer
public class ServiceRegistryApplication {

    /**
     * Starts the Eureka Server Spring Boot application.
     *
     * @param args String[] the command-line arguments passed to the application.
     * @return nothing; the Eureka dashboard becomes available on port 8761.
     */
    public static void main(String[] args) {
        SpringApplication.run(ServiceRegistryApplication.class, args); // Boot the application context.
    } // end main
} // end ServiceRegistryApplication
