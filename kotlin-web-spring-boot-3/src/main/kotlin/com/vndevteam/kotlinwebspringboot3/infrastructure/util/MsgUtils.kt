package com.vndevteam.kotlinwebspringboot3.infrastructure.util

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder

enum class MESSAGE(val code: String) {
    MSG_1("msg-1"),
    MSG_2("msg-2"),
    ERR_1("err-1")
}

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
