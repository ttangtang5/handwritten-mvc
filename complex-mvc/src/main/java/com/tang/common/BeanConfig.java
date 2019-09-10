package com.tang.common;

import java.util.ArrayList;
import java.util.List;

public class BeanConfig {

    private String id;
    private String clsName;
    private String scope;

    private List<BeanProperty> beanPropertys = new ArrayList<BeanProperty>();

    public List<BeanProperty> getBeanPropertys() {
        return beanPropertys;
    }
    public void setBeanPropertys(List<BeanProperty> beanPropertys) {
        this.beanPropertys = beanPropertys;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getClsName() {
        return clsName;
    }
    public void setClsName(String clsName) {
        this.clsName = clsName;
    }
    public String getScope() {
        return scope;
    }
    public void setScope(String scope) {
        this.scope = scope;
    }
    public BeanConfig(String id, String clsName, String scope) {
        super();
        this.id = id;
        this.clsName = clsName;
        this.scope = scope;
    }
    public BeanConfig() {
        super();
    }
    @Override
    public String toString() {
        return "BeanConfig [id=" + id + ", clsName=" + clsName + ", scope="
                + scope + "]";
    }
}
