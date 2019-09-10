package com.tang.common;

import java.util.HashMap;
import java.util.Map;

public class AppContext {

    //Every thread share the threadLocal
    private static ThreadLocal<AppContext> threadLocal = new ThreadLocal<AppContext>();

    //Every AppContext object hava a objects that storage request and response.
    private Map<String, Object> objects = new HashMap<String, Object>();

    private AppContext() {
    }

    public static AppContext getInstance() {
        AppContext appContext = threadLocal.get();
        if (appContext == null) {
            appContext = new AppContext();
            threadLocal.set(appContext);
        }
        return appContext;
    }

    public void clearRequestAndResponse() {
        this.objects.clear();
    }

    public static ThreadLocal<AppContext> getThreadLocal() {
        return threadLocal;
    }

    public static void setThreadLocal(ThreadLocal<AppContext> threadLocal) {
        AppContext.threadLocal = threadLocal;
    }

    public Map<String, Object> getObjects() {
        return objects;
    }

    public void setObjects(Map<String, Object> objects) {
        this.objects = objects;
    }
    @Override
    public String toString() {
        return "AppContext [objects=" + objects + "]";
    }
}
