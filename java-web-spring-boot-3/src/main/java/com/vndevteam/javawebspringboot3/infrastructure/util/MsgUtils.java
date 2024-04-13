package com.vndevteam.javawebspringboot3.infrastructure.util;

import com.vndevteam.javawebspringboot3.domain.enums.Message;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class MsgUtils {
    private static MessageSource messageSource;

    public static void setMessageSource(MessageSource messageSource) {
        MsgUtils.messageSource = messageSource;
    }

    public static String getMessage(Message code) {
        return messageSource.getMessage(code.code, null, LocaleContextHolder.getLocale());
    }

    public static String getMessage(Message code, Object... args) {
        return messageSource.getMessage(code.code, args, LocaleContextHolder.getLocale());
    }
}
