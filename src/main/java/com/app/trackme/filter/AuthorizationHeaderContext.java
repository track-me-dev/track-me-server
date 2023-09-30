package com.app.trackme.filter;

import org.springframework.stereotype.Component;

@Component
public class AuthorizationHeaderContext {

    private static final ThreadLocal<String> authorizationHeader = new ThreadLocal<>();

    public static void setAuthorizationHeader(String headerValue) {
        authorizationHeader.set(headerValue);
    }

    public static String getAuthorizationHeader() {
        return authorizationHeader.get();
    }

    public static void clearAuthorizationHeader() {
        authorizationHeader.remove();
    }
}

