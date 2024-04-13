package com.vndevteam.kotlinwebspringboot3.util

import com.vndevteam.kotlinwebspringboot3.common.enums.Message
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder

object MsgUtils {
    private lateinit var messageSource: MessageSource

    fun setMessageSource(messageSource: MessageSource) {
        MsgUtils.messageSource = messageSource
    }

    fun getMessage(code: Message): String {
        return messageSource.getMessage(code.code, null, LocaleContextHolder.getLocale())
    }

    fun getMessage(code: Message, vararg args: Any): String {
        return messageSource.getMessage(code.code, args, LocaleContextHolder.getLocale())
    }
}
