package com.vndevteam.kotlinwebspringboot3.infrastructure.config

import jakarta.annotation.PostConstruct
import java.util.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver

@Configuration
class LocaleConfig {
    @Value("\${app.locale.default}") private lateinit var defaultLocale: String

    @Value("\${app.timezone.default}") private lateinit var defaultTimezone: String

    @PostConstruct
    fun started() {
        TimeZone.setDefault(TimeZone.getTimeZone(defaultTimezone))
    }

    /**
     * Default locale If the 'Accept-Language' header from request is present and it is not empty,
     * we will use locale from it, otherwise — we’ll use default locale, which is ja.
     */
    @Bean("localeResolver")
    fun acceptHeaderLocaleResolver(): LocaleResolver {
        val localeResolver = AcceptHeaderLocaleResolver()
        if (defaultLocale.isNotBlank()) {
            localeResolver.setDefaultLocale(Locale(defaultLocale))
        }
        return localeResolver
    }
}
