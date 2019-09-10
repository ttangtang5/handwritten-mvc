package com.tang.filter;

import com.tang.common.AppContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class AppContextFilter implements Filter {

    public AppContextFilter() {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest arg1, ServletResponse arg2, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)arg1;
        HttpServletResponse response = (HttpServletResponse)arg2;
        AppContext appContext = AppContext.getInstance();
        Map<String, Object> appContextMap = appContext.getObjects();
        appContextMap.put("request", request);
        appContextMap.put("response", response);
        try {
            chain.doFilter(request, response);
        } catch (Exception exception) {
            throw exception;
        } finally {
            appContext.clearRequestAndResponse();
        }
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
    }
}
