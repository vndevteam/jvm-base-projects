package com.vndevteam.kotlinwebspringboot3.application.logging

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.jboss.logging.MDC
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

class MDCLoggingFilter : OncePerRequestFilter {
    companion object {
        private const val X_FORWARDED_FOR = "X-Forwarded-For"
    }

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
            MDC.remove(mdcTokenKey)
            MDC.remove(mdcClientIpKey)
        }
    }

    override fun isAsyncDispatch(request: HttpServletRequest): Boolean {
        return false
    }

    override fun shouldNotFilterErrorDispatch(): Boolean {
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
