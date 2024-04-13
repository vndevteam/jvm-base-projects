package com.vndevteam.javawebspringboot3.infrastructure.config;

import com.vndevteam.javawebspringboot3.infrastructure.logging.MDCLoggingFilter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Setter
@Configuration
@ConfigurationProperties(prefix = "app.slf4j-filter")
public class Slf4jMDCFilterConfig {
    public static final String DEFAULT_RESPONSE_TOKEN_HEADER = "X-Client-Id";

    private String responseHeader = DEFAULT_RESPONSE_TOKEN_HEADER;
    private String requestHeader;

    @Bean
    public FilterRegistrationBean<MDCLoggingFilter> servletRegistrationBean() {
        FilterRegistrationBean<MDCLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        System.out.println("Khuong test");
        System.out.println("responseHeader: " + responseHeader);
        System.out.println("requestHeader: " + requestHeader);
        MDCLoggingFilter log4jMDCFilterFilter =
                new MDCLoggingFilter(responseHeader, requestHeader);
        registrationBean.setFilter(log4jMDCFilterFilter);
        registrationBean.setOrder(2);
        return registrationBean;
    }
}
