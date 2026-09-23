/*
 Natarajan, C. (2026). CIS530-T301 Server-Side Development (2271-1). Bellevue University.
*/
package com.natarajan.week2.enrollment_api;

import com.natarajan.week2.enrollment_api.config.ApiInfoBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class EnrollmentApiApplication {

    private static final Logger gLogger = LoggerFactory.getLogger(EnrollmentApiApplication.class);

    /**
     * Starts the Student Enrollment REST API application and demonstrates
     * ApiInfoBean's prototype scope and lifecycle callbacks.
     * @param args String[] command-line arguments supplied when the application starts.
     * @return No value; the method starts the application process.
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext lContext = SpringApplication.run(EnrollmentApiApplication.class, args);

        // Request the prototype bean twice from the container. Because ApiInfoBean
        // is registered with prototype scope in AppConfig, each call returns a
        // separate instance, which is confirmed below by comparing hashCodes.
        ApiInfoBean lFirstBean = lContext.getBean(ApiInfoBean.class);
        ApiInfoBean lSecondBean = lContext.getBean(ApiInfoBean.class);

        gLogger.info("First ApiInfoBean:  {} | hashCode={}", lFirstBean, lFirstBean.hashCode());
        gLogger.info("Second ApiInfoBean: {} | hashCode={}", lSecondBean, lSecondBean.hashCode());
        gLogger.info("Prototype scope confirmed - different instances: {}", lFirstBean != lSecondBean);

        // Spring does not automatically call destroy callbacks on prototype-scoped
        // beans, so each instance is destroyed manually here to trigger its
        // @PreDestroy method and demonstrate the end of the bean lifecycle.
        AutowireCapableBeanFactory lBeanFactory = lContext.getAutowireCapableBeanFactory();
        lBeanFactory.destroyBean(lFirstBean);
        lBeanFactory.destroyBean(lSecondBean);
    } // end main
} // end EnrollmentApiApplication
