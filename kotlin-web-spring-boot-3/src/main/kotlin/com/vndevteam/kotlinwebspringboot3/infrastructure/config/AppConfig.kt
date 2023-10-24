package com.vndevteam.kotlinwebspringboot3.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import java.util.*

@Configuration
class AppConfig {
    /**
     * Default locale
     * If the 'Accept-Language' header from request is present and it is not empty, we will use locale from it,
     * otherwise — we’ll use default locale, which is ja.
     */
    @Bean
    fun localeResolver(): LocaleResolver {
        val localeResolver = AcceptHeaderLocaleResolver()
        localeResolver.setDefaultLocale(Locale.JAPANESE)
        return localeResolver
    }
}
