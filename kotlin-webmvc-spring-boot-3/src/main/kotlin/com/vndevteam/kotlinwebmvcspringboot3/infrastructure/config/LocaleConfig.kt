package com.vndevteam.kotlinwebmvcspringboot3.infrastructure.config

import java.util.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.i18n.SessionLocaleResolver

@Configuration
class LocaleConfig {
    @Value("\${app.locale.default}") private lateinit var defaultLocale: String

    @Value("\${app.locale.language-parameter}") private lateinit var languageParameter: String

    @Value("\${app.timezone.default}") private lateinit var defaultTimezone: String

    /** Use for stateful web application */
    @Bean("localeResolver")
    fun sessionLocaleResolver(): SessionLocaleResolver {
        val localeResolver = SessionLocaleResolver()
        if (defaultLocale.isNotBlank()) {
            localeResolver.setDefaultLocale(Locale.Builder().setLanguage(defaultLocale).build())
        }

        if (defaultTimezone.isNotBlank()) {
            localeResolver.defaultTimeZone = TimeZone.getTimeZone(defaultTimezone)
        }

        return localeResolver
    }

    /** Use for stateful web application */
    @Bean
    fun localeChangeInterceptor(): LocaleChangeInterceptor {
        val interceptor = LocaleChangeInterceptor()
        interceptor.paramName = languageParameter
        return interceptor
    }
}
