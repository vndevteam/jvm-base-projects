package com.vndevteam.kotlinwebspringboot3.infrastructure.logging

import jakarta.servlet.http.HttpServletRequest
import org.apache.commons.logging.Log
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.test.util.ReflectionTestUtils

class RequestLoggingFilterTest {
    private lateinit var requestLoggingFilter: RequestLoggingFilterForTest

    private lateinit var logger: Log

    @BeforeEach
    fun setUp() {
        requestLoggingFilter = RequestLoggingFilterForTest()
        logger = mock<Log>()
        ReflectionTestUtils.setField(requestLoggingFilter, "logger", logger)
    }

    @Test
    fun `test beforeRequest when enableLogRequest is true and should log info message`() {
        val req = MockHttpServletRequest()
        ReflectionTestUtils.setField(requestLoggingFilter, "enableLogRequest", true)

        requestLoggingFilter.callBeforeRequest(req, "Test Message")

        verify(logger).info("Test Message")
    }

    @Test
    fun `test beforeRequest when enableLogRequest is false and should not log info message`() {
        val request = MockHttpServletRequest()

        requestLoggingFilter.callBeforeRequest(request, "Test Message")

        verify(logger, never()).info(anyString())
    }
}

class RequestLoggingFilterForTest : RequestLoggingFilter() {
    fun callBeforeRequest(request: HttpServletRequest, message: String) {
        beforeRequest(request, message)
    }
}
