package com.tang.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @FileName: BeanConfig
 * @Author: 16
 * @Date: 2018/8/29 11:19
 * @Description: 用于存储配置文件bean的配置信息
 */
public class BeanConfig {

    private String id;

    private String cls;

    private Boolean scope;

    /**
     * 一对多 bean的属性设置。为什么用map 而不用list map可以通过唯一值作为键迅速判断是是否存在
     */
    private Map<String, BeanProperty> beanProperty = new HashMap<String,BeanProperty>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }

    public Boolean getScope() {
        return scope;
    }

    public void setScope(Boolean scope) {
        this.scope = scope;
    }

    public Map<String, BeanProperty> getBeanProperty() {
        return beanProperty;
    }

    public void setBeanProperty(Map<String, BeanProperty> beanProperty) {
        this.beanProperty = beanProperty;
    }


}
