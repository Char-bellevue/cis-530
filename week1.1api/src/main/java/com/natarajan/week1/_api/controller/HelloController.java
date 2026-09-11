/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/

package com.natarajan.week1._api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * HelloController - REST controller for handling API requests for CIS 530 assignment.
 * Provides endpoints to return welcome messages and course information.
 */
@RestController
public class HelloController {

    /**
     * Handles GET requests to /api/hello endpoint.
     * Returns a plain-text welcome message with the user's name.
     * @return String - A welcome message welcoming the user to CIS-530 Course
     */
    @GetMapping("/api/hello")
    public String hello() {
        return "Natarajan, Welcome to CIS-530 Course!";
    } // end hello

    /**
     * Handles GET requests to /api/info endpoint.
     * Returns a JSON object containing course and technology information.
     * @return Map<String, Object> - A map containing course, week, and technology information
     */
    @GetMapping("/api/info")
    public Map<String, Object> info() {
        Map<String, Object> lData = new LinkedHashMap<>();
        lData.put("course", "CIS 530");
        lData.put("week", 1);
        lData.put("technology", "Spring Boot 4");
        return lData;
    } // end info

} // end HelloController
