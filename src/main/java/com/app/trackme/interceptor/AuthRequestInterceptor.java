package com.app.trackme.interceptor;

import com.app.trackme.filter.AuthorizationHeaderContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        String authorizationHeader = AuthorizationHeaderContext.getAuthorizationHeader();
        template.header("Authorization", authorizationHeader);
    }
}

