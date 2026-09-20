/*
 Natarajan, C. (2026). CIS530-T301 Server-Side Development (2271-1). Bellevue University.
*/
package com.natarajan.week2.enrollment_api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EnrollmentApiApplication {

    private static final Logger gLogger = LoggerFactory.getLogger(EnrollmentApiApplication.class);

    /**
     * Starts the Student Enrollment REST API application.
     * @param args String[] command-line arguments supplied when the application starts.
     * @return No value; the method starts the application process.
     */
    public static void main(String[] args) {
        ApiInfoBean lApiInfoBean = new ApiInfoBean("Student Enrollment API", "1.0");
        gLogger.info("ApiInfoBean: {}", lApiInfoBean);
        gLogger.info("ApiInfoBean hashCode: {}", lApiInfoBean.hashCode());
        SpringApplication.run(EnrollmentApiApplication.class, args);
    } // end main
} // end EnrollmentApiApplication
