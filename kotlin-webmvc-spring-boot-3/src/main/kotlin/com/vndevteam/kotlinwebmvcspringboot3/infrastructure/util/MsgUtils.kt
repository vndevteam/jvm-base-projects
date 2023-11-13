package com.vndevteam.kotlinwebmvcspringboot3.infrastructure.util

import com.vndevteam.kotlinwebmvcspringboot3.domain.enums.MESSAGE
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder

object MsgUtils {
    private lateinit var messageSource: MessageSource

    fun setMessageSource(messageSource: MessageSource) {
        this.messageSource = messageSource
    }

    fun getMessage(code: MESSAGE): String {
        return messageSource.getMessage(code.code, null, LocaleContextHolder.getLocale())
    }

    fun getMessage(code: MESSAGE, vararg args: Any): String {
        return messageSource.getMessage(code.code, args, LocaleContextHolder.getLocale())
    }
}
