package com.natarajan.week2.enrollment_api;

import java.util.Objects;

public class ApiInfoBean {
    private final String name;
    private final String version;

    public ApiInfoBean(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, version);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ApiInfoBean that = (ApiInfoBean) obj;
        return Objects.equals(name, that.name) && Objects.equals(version, that.version);
    }

    @Override
    public String toString() {
        return "ApiInfoBean{name='" + name + "', version='" + version + "'}";
    }
}
