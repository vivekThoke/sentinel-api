package com.project.sentinel_api.config;

import com.project.sentinel_api.filter.ApiKeyAuthFilter;
import com.project.sentinel_api.filter.RequestTrackingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<ApiKeyAuthFilter> apiKeyFilter(ApiKeyAuthFilter filter){
        FilterRegistrationBean<ApiKeyAuthFilter> registration = new FilterRegistrationBean<>();

        registration.setFilter(filter);
        registration.addUrlPatterns("/api/*");
        registration.setOrder(1);

        return registration;

    }

    @Bean
    public FilterRegistrationBean<RequestTrackingFilter> trackingFilter(RequestTrackingFilter filter){
        FilterRegistrationBean<RequestTrackingFilter> registration = new FilterRegistrationBean<>();

        registration.setFilter(filter);
        registration.addUrlPatterns("/api/*");
        registration.setOrder(2);

        return  registration;
    }
}
