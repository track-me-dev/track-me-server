package com.app.trackme.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<AuthorizationHeaderFilter> authorizationHeaderFilter() {
        FilterRegistrationBean<AuthorizationHeaderFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthorizationHeaderFilter());
        registrationBean.addUrlPatterns("/tracks/*");
        return registrationBean;
    }
}

