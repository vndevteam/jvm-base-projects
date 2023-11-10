package com.vndevteam.kotlinwebspringboot3.infrastructure.logging

import com.vndevteam.kotlinwebspringboot3.infrastructure.config.Slf4jMDCFilterConfig
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.jboss.logging.MDC
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.sql.Timestamp
import java.time.Duration
import java.time.Instant
import java.util.*


@Component
class MDCLoggingFilter : OncePerRequestFilter {
    companion object {
        private const val X_FORWARDED_FOR = "X-Forwarded-For"
        val log: Logger = LoggerFactory.getLogger(MDCLoggingFilter::class.java)
    }

    @Value("\${app.logging.log-response-time.enable}")
    val enableMeasureTime: Boolean = false

    @Value("\${app.logging.log-response-time.exclude}")
    val excludeApiPath: List<String>? = emptyList()

    private var responseHeader: String? = null
    private var mdcTokenKey: String? = null
    private var mdcClientIpKey: String? = null
    private var requestHeader: String? = null

    constructor() {
        this.responseHeader = Slf4jMDCFilterConfig.DEFAULT_RESPONSE_TOKEN_HEADER
        this.mdcTokenKey = Slf4jMDCFilterConfig.DEFAULT_MDC_UUID_TOKEN_KEY
        this.mdcClientIpKey = Slf4jMDCFilterConfig.DEFAULT_MDC_CLIENT_IP_KEY
        this.requestHeader = null
    }

    constructor(responseHeader: String, mdcTokenKey: String, mdcClientIpKey: String, requestHeader: String?) {
        this.responseHeader = responseHeader
        this.mdcTokenKey = mdcTokenKey
        this.mdcClientIpKey = mdcClientIpKey
        this.requestHeader = requestHeader
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val start = Instant.now()
        try {
            val clientId: String = getClientId(request)
            val clientIp: String = getClientIpAddress(request)
            MDC.put(mdcClientIpKey, clientIp)
            MDC.put(mdcTokenKey, clientId)

            if (!responseHeader.isNullOrBlank()) {
                response.addHeader(responseHeader, clientId)
            }

            filterChain.doFilter(request, response)
        } finally {
            if (enableMeasureTime && excludeApiPath?.any { request.requestURL.toString().contains(it) } == false) {
                val finish = Instant.now()
                val time: Long = Duration.between(start, finish).toMillis()
                log.info("Request URL: ${request.requestURL} - Take Time: $time millisecond (start: ${Timestamp.from(start)}, end: ${Timestamp.from(finish)} ).")
            }
            MDC.remove(mdcTokenKey)
            MDC.remove(mdcClientIpKey)
        }
    }

    override fun isAsyncDispatch(request: HttpServletRequest): Boolean {
        return false
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
            if (!requestHeader.isNullOrBlank() && !request.getHeader(requestHeader).isNullOrBlank()) {
                request.getHeader(requestHeader)
            } else {
                UUID.randomUUID().toString()
            }
        return clientId
    }
}
