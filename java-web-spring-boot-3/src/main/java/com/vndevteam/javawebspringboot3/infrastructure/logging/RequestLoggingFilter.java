package com.vndevteam.javawebspringboot3.infrastructure.logging;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
@Slf4j
public class RequestLoggingFilter extends CommonsRequestLoggingFilter {
    private static final String HEADER_NAME_API_KEY = "api-key";

    @Value("${app.logging.enable-log-request}")
    private boolean enableLogRequest;

    public RequestLoggingFilter() {
        setIncludeClientInfo(true);
        setIncludeQueryString(true);
        setIncludePayload(true);
        setMaxPayloadLength(10000);
        setIncludeHeaders(true);
        super.setBeforeMessagePrefix("REQUEST DATA: ");
        // Hide sensitive header if you need
        super.setHeaderPredicate(header -> !header.equals(HEADER_NAME_API_KEY));
    }

    @Override
    protected void beforeRequest(@NonNull HttpServletRequest request, @NonNull String message) {
        if (enableLogRequest) {
            log.info(message);
        }
    }

    @Override
    protected void afterRequest(@NonNull HttpServletRequest request, @NonNull String message) {
    }
}
