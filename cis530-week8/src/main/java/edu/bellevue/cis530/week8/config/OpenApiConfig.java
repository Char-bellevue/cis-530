/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week8.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class that sets the title, description, version, and contact
 * details shown at the top of the generated Swagger UI page.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Builds the OpenAPI object that springdoc uses to describe the Student Services API.
     * No parameters.
     * @return OpenAPI, the documentation header with the title, description, version, and contact.
     */
    @Bean
    public OpenAPI studentServicesOpenApi() {
        // Describe the API in a way that a new developer can understand quickly.
        Info lInfo = new Info()
                .title("Student Services API - Bellevue University")
                .description("OpenAPI documentation for the CIS 530 Student Services API.")
                .version("v1.0.0")
                .contact(new Contact().name("Charanya Natarajan"));
        return new OpenAPI().info(lInfo);
    } // end studentServicesOpenApi
} // end OpenApiConfig
