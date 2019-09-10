package com.tang.exception;

import java.util.HashMap;
import java.util.Map;

public class ParameterException extends Exception {

    private static final long serialVersionUID = 1L;

    private Map<String,Object> errorFields = new HashMap<String,Object>();

    public Map<String, Object> getErrorFields() {
        return errorFields;
    }

    public void setErrorFields(Map<String, Object> errorFields) {
        this.errorFields = errorFields;
    }

    public void addErrorFields(String fieldName, Object value) {
        errorFields.put(fieldName, value);
    }

    public boolean isEmpty() {
        return errorFields.isEmpty();
    }
}
