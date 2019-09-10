package com.tang.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author RLY
 * @Date 2018/11/29 10:50
 * @Version 1.0
 **/
public class ModelAndView {

    private String view;

    private boolean isRedirect;

    private Map<String, Object> request = new HashMap<String, Object>();

    private Map<String, Object> session = new HashMap<String, Object>();

    public String getView() {
        return view;
    }

    public ModelAndView setView(String view) {
        this.view = view;
        return this;
    }

    public boolean isRedirect() {
        return isRedirect;
    }

    public ModelAndView setRedirect(boolean redirect) {
        isRedirect = redirect;
        return this;
    }

    public Map<String, Object> getRequest() {
        return request;
    }

    public ModelAndView setRequest(Map<String, Object> request) {
        this.request = request;
        return this;
    }

    public Map<String, Object> getSession() {
        return session;
    }

    public ModelAndView setSession(Map<String, Object> session) {
        this.session = session;
        return this;
    }
}
