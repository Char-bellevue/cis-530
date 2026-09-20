/*
 Natarajan, C. (2026). CIS530-T301 Server-Side Development (2271-1). Bellevue University.
*/
package com.natarajan.week2.enrollment_api;

import java.util.Objects;

public class ApiInfoBean {
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
     * Calculates a hash code from the API name and version.
     * @return int hash code for this API metadata object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(gName, gVersion);
    } // end hashCode

    /**
     * Compares this API metadata object with another object.
     * @param obj Object to compare with this API metadata object.
     * @return boolean true when both objects contain the same name and version.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ApiInfoBean lApiInfoBean = (ApiInfoBean) obj;
        return Objects.equals(gName, lApiInfoBean.gName)
                && Objects.equals(gVersion, lApiInfoBean.gVersion);
    } // end equals

    /**
     * Creates a readable description of this API metadata object.
     * @return String containing the API name and version.
     */
    @Override
    public String toString() {
        return "ApiInfoBean{name='" + gName + "', version='" + gVersion + "'}";
    } // end toString
} // end ApiInfoBean
