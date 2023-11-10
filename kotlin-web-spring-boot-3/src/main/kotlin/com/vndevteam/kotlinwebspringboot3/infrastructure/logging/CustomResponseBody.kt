package com.vndevteam.kotlinwebspringboot3.infrastructure.logging

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.http.server.ServletServerHttpResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

@ControllerAdvice
class CustomResponseBody : ResponseBodyAdvice<Any> {
    companion object {
        val log: Logger = LoggerFactory.getLogger(CustomResponseBody::class.java)
    }

    @Value("\${app.logging.enable-log-response}") val enableLogResponse: Boolean = false

    override fun supports(
        returnType: MethodParameter,
        converterType: Class<out HttpMessageConverter<*>>
    ): Boolean {
        return true
    }

    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): Any? {
        if (
            enableLogResponse &&
                request is ServletServerHttpRequest &&
                response is ServletServerHttpResponse
        ) {
            loggingResponse(request.servletRequest, response.servletResponse, body)
        }

        return body
    }

    fun loggingResponse(request: HttpServletRequest, response: HttpServletResponse, body: Any?) {
        val respMessage = StringBuilder()
        val headers = getHeaders(response)
        respMessage.append("RESPONSE ")
        respMessage.append(" method = [").append(request.method).append("]")
        if (headers.isNotEmpty()) {
            respMessage.append(" ResponseHeaders = [").append(headers).append("]")
        }
        respMessage.append(" responseBody = [").append(body).append("]")
        log.info("Log response: {}", respMessage)
    }

    private fun getHeaders(response: HttpServletResponse): Map<String, String> {
        val headers: MutableMap<String, String> = HashMap()
        val headerMap = response.headerNames
        for (str in headerMap) {
            headers[str] = response.getHeader(str)
        }
        return headers
    }
}
