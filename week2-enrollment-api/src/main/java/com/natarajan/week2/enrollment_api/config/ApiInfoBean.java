/*
 Natarajan, C. (2026). CIS530-T301 Server-Side Development (2271-1). Bellevue University.
*/
package com.natarajan.week2.enrollment_api.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiInfoBean {

    private static final Logger gLogger = LoggerFactory.getLogger(ApiInfoBean.class);

    private final String gName;
    private final String gVersion;

    /**
     * Creates API metadata with a name and version.
     * @param name String API name.
     * @param version String API version.
     * @return No value; the constructor initializes the object.
     */
    public ApiInfoBean(String name, String version) {
        this.gName = name;
        this.gVersion = version;
    } // end ApiInfoBean

    /**
     * Logs a message when Spring finishes constructing this bean, demonstrating
     * the initialization phase of the bean lifecycle.
     * @return No value; the method only logs a lifecycle message.
     */
    @PostConstruct
    public void onCreate() {
        gLogger.info("ApiInfoBean lifecycle - PostConstruct - bean created: {} (hashCode={})", this, hashCode());
    } // end onCreate

    /**
     * Logs a message when this bean is destroyed, demonstrating the destruction
     * phase of the bean lifecycle. Spring does not call this automatically for
     * prototype-scoped beans, so it is invoked manually where the bean is used.
     * @return No value; the method only logs a lifecycle message.
     */
    @PreDestroy
    public void onDestroy() {
        gLogger.info("ApiInfoBean lifecycle - PreDestroy - bean destroyed: {} (hashCode={})", this, hashCode());
    } // end onDestroy

    /**
     * Returns the API name.
     * @return String containing the API name.
     */
    public String getName() {
        return gName;
    } // end getName

    /**
     * Returns the API version.
     * @return String containing the API version.
     */
    public String getVersion() {
        return gVersion;
    } // end getVersion

    /**
     * Creates a readable description of this API metadata object.
     * @return String containing the API name and version.
     */
    @Override
    public String toString() {
        return "ApiInfoBean{name='" + gName + "', version='" + gVersion + "'}";
    } // end toString
} // end ApiInfoBean
