package com.vndevteam.kotlinwebspringboot3.infrastructure.logging

import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.CommonsRequestLoggingFilter

@Configuration
class RequestLoggingFilter : CommonsRequestLoggingFilter() {
    companion object {
        private const val HEADER_NAME_API_KEY = "api-key"
    }

    @Value("\${app.logging.enable-log-request}") val enableLogRequest: Boolean = false

    init {
        isIncludeClientInfo = true
        isIncludeQueryString = true
        isIncludePayload = true
        maxPayloadLength = 10000
        isIncludeHeaders = true
        super.setBeforeMessagePrefix("REQUEST DATA: ")
        // Hide sensitive header if you need
        super.setHeaderPredicate { header -> header != HEADER_NAME_API_KEY }
    }

    override fun beforeRequest(request: HttpServletRequest, message: String) {
        if (enableLogRequest) {
            logger.info(message)
        }
    }

    override fun afterRequest(request: HttpServletRequest, message: String) {}
}
