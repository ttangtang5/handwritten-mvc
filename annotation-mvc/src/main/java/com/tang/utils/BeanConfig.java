package com.tang.utils;

import java.util.Map;

/**
 * @Description BeanConfig 用于存放类的基本信息
 * @Author RLY
 * @Date 2018/11/28 10:37
 * @Version 1.0
 **/
public class BeanConfig {

    private String name;

    private String cls;

    private boolean isSingleton = true;

    private Map<String, Properties> propertiesMap;

    public BeanConfig() {
    }

    public BeanConfig(String name, String cls, boolean isSingleton) {
        this.name = name;
        this.cls = cls;
        this.isSingleton = isSingleton;
    }

    public String getName() {
        return name;
    }

    public BeanConfig setName(String name) {
        this.name = name;
        return this;
    }

    public String getCls() {
        return cls;
    }

    public BeanConfig setCls(String cls) {
        this.cls = cls;
        return this;
    }

    public boolean isSingleton() {
        return isSingleton;
    }

    public BeanConfig setSingleton(boolean singleton) {
        isSingleton = singleton;
        return this;
    }

    public Map<String, Properties> getPropertiesMap() {
        return propertiesMap;
    }

    public BeanConfig setPropertiesMap(Map<String, Properties> propertiesMap) {
        this.propertiesMap = propertiesMap;
        return this;
    }
}
