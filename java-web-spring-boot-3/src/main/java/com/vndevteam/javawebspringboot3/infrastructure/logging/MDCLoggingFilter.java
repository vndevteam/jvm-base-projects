package com.vndevteam.javawebspringboot3.infrastructure.logging;

import com.vndevteam.javawebspringboot3.infrastructure.config.Slf4jMDCFilterConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class MDCLoggingFilter extends OncePerRequestFilter {
    public static final String MDC_TOKEN_KEY = "RequestFilter.UUID";
    public static final String MDC_CLIENT_IP_KEY = "RequestFilter.ClientIP";

    @Value("${app.logging.log-response-time.enable}")
    Boolean enableMeasureTime = false;

    @Value("${app.logging.log-response-time.exclude}")
    List<String> excludeApiPath = List.of();

    @Value("${app.logging.log-response-time.include}")
    List<String> includeApiPath = List.of();

    private final String responseHeader;
    private final String requestHeader;

    public MDCLoggingFilter() {
        this.responseHeader = Slf4jMDCFilterConfig.DEFAULT_RESPONSE_TOKEN_HEADER;
        this.requestHeader = null;
    }

    public MDCLoggingFilter(String responseHeader, String requestHeader) {
        this.responseHeader = responseHeader;
        this.requestHeader = requestHeader;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        Instant start = Instant.now();
        try {
            if (MDC.get(MDC_CLIENT_IP_KEY) == null) {
                MDC.put(MDC_CLIENT_IP_KEY, getClientIpAddress(request));
            }

            if (MDC.get(MDC_TOKEN_KEY) == null && !responseHeader.isBlank()) {
                String clientId = getClientId(request);
                MDC.put(MDC_TOKEN_KEY, clientId);
                response.addHeader(responseHeader, clientId);
            }

            filterChain.doFilter(request, response);
        } finally {
            if (enableMeasureTime
                    && isLoggingRequest(
                    includeApiPath, excludeApiPath, request.getRequestURL().toString())) {
                Instant finish = Instant.now();
                long time = Duration.between(start, finish).toMillis();
                log.info("Request URL: {} - status: {} - Take Time: {} millisecond (start: {}, end: {}).",
                        request.getRequestURL(), response.getStatus(), time, Timestamp.from(start), Timestamp.from(finish));
            }
            MDC.remove(MDC_TOKEN_KEY);
            MDC.remove(MDC_CLIENT_IP_KEY);
        }
    }

    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return true;
    }

    private String getClientIpAddress(HttpServletRequest httpHeaders) {
        String xForwardedHeader = httpHeaders.getHeader("X-Forwarded-For");
        if (xForwardedHeader == null || xForwardedHeader.isBlank()) {
            return httpHeaders.getRemoteAddr();
        }
        return xForwardedHeader.split(",")[0].trim();
    }

    private String getClientId(HttpServletRequest request) {
        String clientId;
        if (requestHeader != null
                && !requestHeader.isBlank()
                && request.getHeader(requestHeader) != null
                && !request.getHeader(requestHeader).isBlank()) {
            clientId = request.getHeader(requestHeader);
        } else {
            clientId = UUID.randomUUID().toString();
        }
        return clientId;
    }

    private boolean isLoggingRequest(
            List<String> includeApiPath, List<String> excludeApiPath, String requestUrl) {
        if (includeApiPath.isEmpty() && excludeApiPath.isEmpty()) {
            return true;
        } else if (!includeApiPath.isEmpty() && excludeApiPath.isEmpty()) {
            return includeApiPath.stream().anyMatch(requestUrl::contains);
        } else if (includeApiPath.isEmpty()) {
            return excludeApiPath.stream().noneMatch(requestUrl::contains);
        } else {
            return includeApiPath.stream().anyMatch(requestUrl::contains);
        }
    }
}
