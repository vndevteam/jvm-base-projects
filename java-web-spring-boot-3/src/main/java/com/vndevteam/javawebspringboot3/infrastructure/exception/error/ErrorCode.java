package com.vndevteam.javawebspringboot3.infrastructure.exception.error;

import lombok.Getter;

@Getter
public class ErrorCode {
    public static final String SYSTEM_ERROR = "100";
    public static final String REQUEST_METHOD_INVALID = "101";
    public static final String MEDIA_TYPE_INVALID = "102";
    public static final String VALIDATION_INVALID = "103";
    public static final String REQUEST_HEADER_INVALID = "104";
}
