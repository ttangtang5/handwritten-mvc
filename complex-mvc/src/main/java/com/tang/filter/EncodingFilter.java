package com.tang.filter;


import com.tang.Constants;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {

    private FilterConfig fConfig = null;

    public EncodingFilter() {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String encoding = fConfig.getInitParameter("encoding");

        if (encoding == null) {
            request.setCharacterEncoding(Constants.DEFAULT_ENCODING);
        } else {
            request.setCharacterEncoding(encoding);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        this.fConfig = fConfig;
    }

}
