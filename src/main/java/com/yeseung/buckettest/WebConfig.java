package com.yeseung.buckettest;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Bean
    public FilterRegistrationBean<ThrottlingFilter> throttlingFilterFilterRegistrationBean() {
        FilterRegistrationBean<ThrottlingFilter> registrationBean = new FilterRegistrationBean<>(new ThrottlingFilter());
        registrationBean.setName("throttling");
        return registrationBean;
    }



}
