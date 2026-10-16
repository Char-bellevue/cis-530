/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
Class supplied in the CIS 530 Assignment 6.2 instructions. Bellevue University.
*/
package edu.bellevue.cis530.week6.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class that creates the reusable WebClient bean used to call JSONPlaceholder.
 */
@Configuration
public class WebClientConfig {

    /**
     * Builds the WebClient with the base URL, JSON default headers, and a larger memory buffer.
     * @param baseUrl String the JSONPlaceholder base URL read from application.properties.
     * @return WebClient a configured client that is shared across the application.
     */
    @Bean
    public WebClient webClient(@Value("${jsonplaceholder.api.base-url}") String baseUrl) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024))
                        .build())
                .build();
    } // end webClient
} // end WebClientConfig
