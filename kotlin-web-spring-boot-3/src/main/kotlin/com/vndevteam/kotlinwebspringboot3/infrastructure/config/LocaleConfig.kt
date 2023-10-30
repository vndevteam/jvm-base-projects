package com.vndevteam.kotlinwebspringboot3.infrastructure.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.*


@Configuration
class LocaleConfig {
    @Value("\${app.locale.default}")
    private lateinit var defaultLocale: String

    @Value("\${app.locale.language-parameter}")
    private lateinit var languageParameter: String

    /**
     * Default locale
     * If the 'Accept-Language' header from request is present and it is not empty, we will use locale from it,
     * otherwise — we’ll use default locale, which is ja.
     */
    @Bean
    fun localeResolver(): LocaleResolver {
        val localeResolver = AcceptHeaderLocaleResolver()
        if (defaultLocale.isNotBlank()) {
            localeResolver.setDefaultLocale(Locale(defaultLocale))
        }
        return localeResolver
    }

    @Bean
    fun sessionLocaleResolver(): SessionLocaleResolver {
        val localeResolver = SessionLocaleResolver()
        if (defaultLocale.isNotBlank()) {
            localeResolver.setDefaultLocale(Locale(defaultLocale))
        }
        return localeResolver
    }


    @Bean
    fun localeChangeInterceptor(): LocaleChangeInterceptor {
        val interceptor = LocaleChangeInterceptor()
        interceptor.paramName = languageParameter
        return interceptor
    }
}
