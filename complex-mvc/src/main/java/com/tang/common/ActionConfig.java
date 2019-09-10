package com.tang.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ActionConfig {

    private String name;
    private String clsName;
    private String methodName;
    private String[] httpMethod;
    private Map<String, ResultConfig> results = new HashMap<String, ResultConfig>();

    public Map<String, ResultConfig> getResults() {
        return results;
    }
    public void setResults(Map<String, ResultConfig> results) {
        this.results = results;
    }
    public String[] getHttpMethod() {
        return httpMethod;
    }
    public void setHttpMethod(String[] httpMethod) {
        this.httpMethod = httpMethod;
    }
    public String getClsName() {
        return clsName;
    }
    public void setClsName(String clsName) {
        this.clsName = clsName;
    }
    public String getMethodName() {
        return methodName;
    }
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ActionConfig() {
        super();
    }
    public ActionConfig(String name, String clsName, String methodName,
            String[] httpMethod, Map<String, ResultConfig> results) {
        super();
        this.name = name;
        this.clsName = clsName;
        this.methodName = methodName;
        this.httpMethod = httpMethod;
        this.results = results;
    }
    @Override
    public String toString() {
        return "ActionConfig [name=" + name + ", clsName=" + clsName
                + ", methodName=" + methodName + ", httpMethod="
                + Arrays.toString(httpMethod) + ", results=" + results + "]";
    }
}
