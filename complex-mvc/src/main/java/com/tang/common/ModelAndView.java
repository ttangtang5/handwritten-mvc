package com.tang.common;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

    private String view;
    private boolean isRedirect;

    private Map<String, Object> sessions = new HashMap<String, Object>();
    private Map<String, Object> requests = new HashMap<String, Object>();

    public String getView() {
        return view;
    }
    public void setView(String view) {
        this.view = view;
    }
    public ModelAndView(String view, boolean isRedirect) {
        super();
        this.view = view;
        this.isRedirect = isRedirect;
    }
    public ModelAndView() {
        super();
    }
    public void removeSessionsAttribute(String key) {
        this.sessions.put(key, "remove");
    }
    public void setSessionAttribute(String key, Object value) {
        this.sessions.put(key, value);
    }
    public void setRequestAttribute(String key, Object value) {
        this.requests.put(key, value);
    }
    public boolean isRedirect() {
        return isRedirect;
    }
    public void setRedirect(boolean isRedirect) {
        this.isRedirect = isRedirect;
    }
    public Map<String, Object> getRequests() {
        return requests;
    }
    public void setRequests(Map<String, Object> requests) {
        this.requests = requests;
    }
    public Map<String, Object> getSessions() {
        return sessions;
    }
    public void setSessions(Map<String, Object> sessions) {
        this.sessions = sessions;
    }
    @Override
    public String toString() {
        return "ModelAndView [view=" + view + ", isRedirect=" + isRedirect
                + ", sessions=" + sessions + ", requests=" + requests + "]";
    }

}
