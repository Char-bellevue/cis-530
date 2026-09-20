package com.natarajan.week2.enrollment_api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EnrollmentApiApplication {

    private static final Logger logger = LoggerFactory.getLogger(EnrollmentApiApplication.class);

    public static void main(String[] args) {
        ApiInfoBean apiInfoBean = new ApiInfoBean("Student Enrollment API", "1.0");
        logger.info("ApiInfoBean: {}", apiInfoBean);
        logger.info("ApiInfoBean hashCode: {}", apiInfoBean.hashCode());
        SpringApplication.run(EnrollmentApiApplication.class, args);
    }
}
