/*
 Natarajan, C. (2026). CIS530-T301 Server-Side Development (2271-1). Bellevue University.
*/
package com.natarajan.week1._api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class HelloController {

    /**
    * Handles GET requests to the /api/hello endpoint.
    * @return String containing a welcome message for the CIS 530 course.
     */
    @GetMapping("/api/hello")
    public String hello() {
        return "Welcome to CIS 530 – Week 1 REST API!";
    } // end hello

    /**
        * Handles GET requests to the /api/info endpoint.
        * @return Map<String, Object> containing course, week, and technology information.
     */
    @GetMapping("/api/info")
    public Map<String, Object> info() {
        Map<String, Object> lData = new LinkedHashMap<>();
        lData.put("course", "CIS 530");
        lData.put("week", 1);
        lData.put("technology", "Spring Boot 4");
        lData.put("university", "Bellevue University");
        lData.put("semester", "Spring 2026");
        return lData;
    } // end info

} // end HelloController
