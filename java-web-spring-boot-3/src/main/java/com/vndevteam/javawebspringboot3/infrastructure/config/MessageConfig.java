package com.vndevteam.javawebspringboot3.infrastructure.config;

import com.vndevteam.javawebspringboot3.infrastructure.util.MsgUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class MessageConfig {
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasename("message/messages");
        source.setDefaultEncoding("UTF-8");
        return source;
    }

    @PostConstruct
    public void setMessageSource() {
        MsgUtils.setMessageSource(messageSource());
    }

    /**
     * Register MessageSource bean with the LocalValidatorFactoryBean to use custom validation
     * message (get from messages.properties) Ex: @NotEmpty(message = "{name.not.empty}")
     */
    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}
