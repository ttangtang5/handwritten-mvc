package com.tang.common;

/**
 * @FileName: BeanProperty
 * @Author: 16
 * @Date: 2018/8/29 11:18
 * @Description: 用于存储Bean的属性配置
 */
public class BeanProperty {

    private String name;

    private String ref;

    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
