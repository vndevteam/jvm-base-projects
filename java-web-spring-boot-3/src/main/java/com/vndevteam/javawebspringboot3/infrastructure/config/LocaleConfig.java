package com.vndevteam.javawebspringboot3.infrastructure.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;
import java.util.TimeZone;

@Configuration
public class LocaleConfig {
    @Value("${app.locale.default}")
    private String defaultLocale;

    @Value("${app.timezone.default}")
    private String defaultTimezone;

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone(defaultTimezone));
    }

    /**
     * Default locale If the 'Accept-Language' header from request is present, and it is not empty,
     * we will use locale from it, otherwise — we’ll use default locale, which is ja.
     */
    @Bean("localeResolver")
    public LocaleResolver acceptHeaderLocaleResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        if (!defaultLocale.isBlank()) {
            localeResolver.setDefaultLocale(Locale.forLanguageTag(defaultLocale));
        }

        return localeResolver;
    }
}
