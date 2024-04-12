package com.vndevteam.kotlinwebspringboot3.infrastructure.config

import com.vndevteam.kotlinwebspringboot3.infrastructure.logging.MDCLoggingFilter
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "app.slf4j-filter")
class Slf4jMDCFilterConfig {
    companion object {
        const val DEFAULT_RESPONSE_TOKEN_HEADER = "X-Client-Id"
    }

    var responseHeader: String = DEFAULT_RESPONSE_TOKEN_HEADER
    var requestHeader: String? = null

    @Bean
    fun servletRegistrationBean(): FilterRegistrationBean<MDCLoggingFilter>? {
        val registrationBean: FilterRegistrationBean<MDCLoggingFilter> =
            FilterRegistrationBean<MDCLoggingFilter>()
        val log4jMDCFilterFilter = MDCLoggingFilter(responseHeader, requestHeader)
        registrationBean.filter = log4jMDCFilterFilter
        registrationBean.order = 2
        return registrationBean
    }
}
