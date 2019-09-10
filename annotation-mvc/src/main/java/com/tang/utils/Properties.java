package com.tang.utils;

/**
 * @Description bean中属性设置
 * @Author RLY
 * @Date 2018/11/28 16:43
 * @Version 1.0
 **/
public class Properties {

    private String name;

    private Object value;

    public String getName() {
        return name;
    }

    public Properties setName(String name) {
        this.name = name;
        return this;
    }

    public Object getValue() {
        return value;
    }

    public Properties setValue(Object value) {
        this.value = value;
        return this;
    }
}
