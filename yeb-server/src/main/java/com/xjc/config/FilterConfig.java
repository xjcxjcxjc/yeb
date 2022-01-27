package com.xjc.config;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.FormContentFilter;

/**
 * @Author : XJC
 * @Time : 2022/1/6 15:11
 * @Description :
 */
@Configuration
public class FilterConfig {

    public FilterRegistrationBean  formContentFilter() {
        FilterRegistrationBean userFilter = new FilterRegistrationBean();
        userFilter.addUrlPatterns("/*");
        userFilter.setFilter(new FormContentFilter());
        return userFilter ;
    }

}
