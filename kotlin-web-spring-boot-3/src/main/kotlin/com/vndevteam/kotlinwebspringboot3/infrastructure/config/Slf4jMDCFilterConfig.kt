package com.vndevteam.kotlinwebspringboot3.infrastructure.config

import com.vndevteam.kotlinwebspringboot3.infrastructure.logging.MDCLoggingFilter
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "app.slf4jfilter")
class Slf4jMDCFilterConfig {
    companion object {
        const val DEFAULT_RESPONSE_TOKEN_HEADER = "X-Client-Id"
        const val DEFAULT_MDC_UUID_TOKEN_KEY = "RequestFilter.UUID"
        const val DEFAULT_MDC_CLIENT_IP_KEY = "RequestFilter.ClientIP"
    }

    var responseHeader: String = DEFAULT_RESPONSE_TOKEN_HEADER
    var mdcTokenKey: String = DEFAULT_MDC_UUID_TOKEN_KEY
    var mdcClientIpKey: String = DEFAULT_MDC_CLIENT_IP_KEY
    var requestHeader: String? = null

    @Bean
    fun servletRegistrationBean(): FilterRegistrationBean<MDCLoggingFilter>? {
        val registrationBean: FilterRegistrationBean<MDCLoggingFilter> =
            FilterRegistrationBean<MDCLoggingFilter>()
        val log4jMDCFilterFilter =
            MDCLoggingFilter(responseHeader, mdcTokenKey, mdcClientIpKey, requestHeader)
        registrationBean.filter = log4jMDCFilterFilter
        registrationBean.order = 2
        return registrationBean
    }
}
