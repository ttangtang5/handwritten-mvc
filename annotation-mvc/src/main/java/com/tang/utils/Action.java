package com.tang.utils;

/**
 * @Description
 * @Author RLY
 * @Date 2018/11/29 10:46
 * @Version 1.0
 **/
public class Action {

    private String path;

    private String name;

    private String actionCls;

    private String methodName;

    public String getPath() {
        return path;
    }

    public Action setPath(String path) {
        this.path = path;
        return this;
    }

    public String getName() {
        return name;
    }

    public Action setName(String name) {
        this.name = name;
        return this;
    }

    public String getActionCls() {
        return actionCls;
    }

    public Action setActionCls(String actionCls) {
        this.actionCls = actionCls;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public Action setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }
}
