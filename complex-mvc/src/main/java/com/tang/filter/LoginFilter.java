package com.tang.filter;


import com.tang.Constants;
import com.tang.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {

    private String notNeedLoginPages = null;
    protected FilterConfig fConfig = null;

    public LoginFilter() {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)arg0;
        HttpServletResponse response = (HttpServletResponse)arg1;
        HttpSession session = request.getSession();

        String uri = request.getRequestURI();

        //Get the resources of uri
        String uriResources = uri.substring(request.getContextPath().length()+1);

        //notNeedLoginPages content include resources that can be passed through filters.
        String pages[] = notNeedLoginPages.split(",");
        boolean isAllow = false;

        for (String page : pages) {
            // whether it is a login request
            if (page != null && page.equals(uriResources)) {
                isAllow = true;
                 break;
            }
        }
        if (isAllow) {
            chain.doFilter(request, response);
        } else {
            User user = (User) session.getAttribute(Constants.USER);
            if (user == null) {
                if (request.getMethod().toLowerCase().equals("get")) {
                    response.sendRedirect(request.getContextPath() + "/login.action?go=" + uriResources);
                } else {
                    response.sendRedirect(request.getContextPath() + "/login.action");
                }
                return;
            } else {
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        this.fConfig = fConfig;
        notNeedLoginPages = fConfig.getInitParameter("notNeedLoginPages");
    }
}
