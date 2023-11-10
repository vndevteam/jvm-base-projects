package com.vndevteam.kotlinwebspringboot3.infrastructure.config

import com.vndevteam.kotlinwebspringboot3.infrastructure.util.MsgUtils
import jakarta.annotation.PostConstruct
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

@Configuration
class MessageConfig {
    fun messageSource(): MessageSource {
        val source = ReloadableResourceBundleMessageSource()
        source.setBasename("message/messages")
        source.setDefaultEncoding("UTF-8")
        return source
    }

    @PostConstruct
    fun setMessageSource() {
        MsgUtils.setMessageSource(messageSource())
    }

    /**
     * Register MessageSource bean with the LocalValidatorFactoryBean to use custom validation
     * message (get from messages.properties) Ex: @NotEmpty(message = "{name.not.empty}")
     */
    @Bean
    fun validator(): LocalValidatorFactoryBean {
        val bean = LocalValidatorFactoryBean()
        bean.setValidationMessageSource(messageSource())
        return bean
    }
}
