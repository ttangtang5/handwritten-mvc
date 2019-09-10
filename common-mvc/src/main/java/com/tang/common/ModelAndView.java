package com.tang.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @FileName: ModelAndView
 * @Author: 16
 * @Date: 2018/8/29 20:13
 * @Description: 视图模型 用于controller的返回
 */
public class ModelAndView {

    /**
     * 视图路径
     */
    private String view;

    private boolean isRedirect;

    private Map<String, Object> requestValue = new HashMap<String, Object>();

    private Map<String, Object> sessionValue = new HashMap<String, Object>();

    public void addRequestValue(String key,Object value){
        requestValue.put(key, value);
    }

    public void addSessionValue(String key,Object value){
        sessionValue.put(key, value);
    }

    public void removeSessionValue(String key){
        //TODO 此处移除标记不严谨
        sessionValue.put(key, "remove");
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public boolean isRedirect() {
        return isRedirect;
    }

    public void setRedirect(boolean redirect) {
        isRedirect = redirect;
    }

    public Map<String, Object> getRequestValue() {
        return requestValue;
    }

    public void setRequestValue(Map<String, Object> requestValue) {
        this.requestValue = requestValue;
    }

    public Map<String, Object> getSessionValue() {
        return sessionValue;
    }

    public void setSessionValue(Map<String, Object> sessionValue) {
        this.sessionValue = sessionValue;
    }
}
