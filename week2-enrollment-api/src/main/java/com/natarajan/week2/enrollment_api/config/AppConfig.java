/*
 Natarajan, C. (2026). CIS530-T301 Server-Side Development (2271-1). Bellevue University.
*/
package com.natarajan.week2.enrollment_api.config;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    /**
     * Registers ApiInfoBean as a prototype-scoped Spring bean, so the container
     * returns a brand-new instance every time the bean is requested.
     * @return ApiInfoBean a new bean instance carrying the API name and version.
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ApiInfoBean apiInfoBean() {
        return new ApiInfoBean("Student Enrollment API", "1.0");
    } // end apiInfoBean
} // end AppConfig
