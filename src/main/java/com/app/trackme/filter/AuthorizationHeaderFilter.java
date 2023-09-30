package com.app.trackme.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthorizationHeaderFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        AuthorizationHeaderContext.setAuthorizationHeader(authorizationHeader);
        chain.doFilter(request, response);
    }
}

