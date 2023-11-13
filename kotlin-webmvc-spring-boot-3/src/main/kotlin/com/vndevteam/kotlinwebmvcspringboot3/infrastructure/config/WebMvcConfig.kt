package com.vndevteam.kotlinwebmvcspringboot3.infrastructure.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor

@Configuration
class WebMvcConfig(private val localeChangeInterceptor: LocaleChangeInterceptor) :
    WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(localeChangeInterceptor)
    }
}
