package com.tang.common;

/**
 * @FileName: ActionConfig
 * @Author: 16
 * @Date: 2018/8/29 16:46
 * @Description: 用于存储配置文件action的配置信息
 */
public class ActionConfig {

    private String name;

    private String cls;

    private String method;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

}
