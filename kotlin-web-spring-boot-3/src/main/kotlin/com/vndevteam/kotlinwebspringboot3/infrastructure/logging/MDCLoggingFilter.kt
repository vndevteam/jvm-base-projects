package com.vndevteam.kotlinwebspringboot3.infrastructure.logging

import com.vndevteam.kotlinwebspringboot3.infrastructure.config.Slf4jMDCFilterConfig
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.sql.Timestamp
import java.time.Duration
import java.time.Instant
import java.util.*
import org.jboss.logging.MDC
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class MDCLoggingFilter : OncePerRequestFilter {
    companion object {
        private const val X_FORWARDED_FOR = "X-Forwarded-For"
        private const val MDC_TOKEN_KEY = "RequestFilter.UUID"
        private const val MDC_CLIENT_IP_KEY = "RequestFilter.ClientIP"
        private val log: Logger = LoggerFactory.getLogger(MDCLoggingFilter::class.java)
    }

    @Value("\${app.logging.log-response-time.enable}") val enableMeasureTime: Boolean = false

    @Value("\${app.logging.log-response-time.exclude}")
    val excludeApiPath: List<String>? = emptyList()

    @Value("\${app.logging.log-response-time.include}")
    val includeApiPath: List<String>? = emptyList()

    private var responseHeader: String? = null
    private var requestHeader: String? = null

    constructor() {
        this.responseHeader = Slf4jMDCFilterConfig.DEFAULT_RESPONSE_TOKEN_HEADER
        this.requestHeader = null
    }

    constructor(
        responseHeader: String,
        requestHeader: String?
    ) {
        this.responseHeader = responseHeader
        this.requestHeader = requestHeader
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val start = Instant.now()
        try {
            if (MDC.get(MDC_CLIENT_IP_KEY) == null) {
                MDC.put(MDC_CLIENT_IP_KEY, getClientIpAddress(request))
            }

            if (MDC.get(MDC_TOKEN_KEY) == null && !responseHeader.isNullOrBlank()) {
                val clientId = getClientId(request)
                MDC.put(MDC_TOKEN_KEY, clientId)
                response.addHeader(responseHeader, clientId)
            }

            filterChain.doFilter(request, response)
        } finally {
            if (
                enableMeasureTime &&
                    isLoggingRequest(
                        includeApiPath!!,
                        excludeApiPath!!,
                        request.requestURL.toString()
                    )
            ) {
                val finish = Instant.now()
                val time: Long = Duration.between(start, finish).toMillis()
                log.info(
                    "Request URL: ${request.requestURL} - Take Time: $time millisecond (start: ${Timestamp.from(start)}, end: ${Timestamp.from(finish)} )."
                )
            }
            MDC.remove(MDC_TOKEN_KEY)
            MDC.remove(MDC_CLIENT_IP_KEY)
        }
    }

    override fun shouldNotFilterAsyncDispatch(): Boolean {
        return true
    }

    private fun getClientIpAddress(httpHeaders: HttpServletRequest): String {
        val xForwardedHeader = httpHeaders.getHeader(X_FORWARDED_FOR)
        if (xForwardedHeader.isNullOrBlank()) {
            return httpHeaders.remoteAddr
        }
        return xForwardedHeader.split(',')[0].trim()
    }

    private fun getClientId(request: HttpServletRequest): String {
        val clientId: String =
            if (
                !requestHeader.isNullOrBlank() && !request.getHeader(requestHeader).isNullOrBlank()
            ) {
                request.getHeader(requestHeader)
            } else {
                UUID.randomUUID().toString()
            }
        return clientId
    }

    private fun isLoggingRequest(
        includeApiPath: List<String>,
        excludeApiPath: List<String>,
        requestUrl: String
    ): Boolean {
        return when {
            includeApiPath.isEmpty() && excludeApiPath.isEmpty() -> {
                true
            }
            includeApiPath.isNotEmpty() && excludeApiPath.isEmpty() -> {
                includeApiPath.any { requestUrl.contains(it) }
            }
            includeApiPath.isEmpty() -> {
                !excludeApiPath.any { requestUrl.contains(it) }
            }
            else -> {
                includeApiPath.any { requestUrl.contains(it) }
            }
        }
    }
}
