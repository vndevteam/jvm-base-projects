package com.vndevteam.kotlinwebspringboot3.infrastructure.exception

import com.vndevteam.kotlinwebspringboot3.infrastructure.exception.error.ErrorDetail
import com.vndevteam.kotlinwebspringboot3.infrastructure.exception.error.ErrorResponseDto
import com.vndevteam.kotlinwebspringboot3.util.DateTimeUtils
import jakarta.validation.ConstraintViolationException
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.util.CollectionUtils
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ExceptionsHandler : ResponseEntityExceptionHandler() {
    companion object {
        val log: Logger = LoggerFactory.getLogger(ExceptionsHandler::class.java)
    }

    @Value("\${app.logging.debug}") val debug: Boolean = false

    /**
     * Handle when method argument is not the expected type
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatch(
        ex: MethodArgumentTypeMismatchException
    ): ResponseEntity<Any> {

        return ResponseEntity(
            ErrorResponseDto(
                timestamp = DateTimeUtils.getNow(),
                code = ErrorConstants.VALIDATION_INVALID,
                message = ex.message,
                trace = getServerMessage(ex),
                errors = null
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    /**
     * Handle when sending a request with an unsupported HTTP method
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    override fun handleHttpRequestMethodNotSupported(
        ex: HttpRequestMethodNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {

        return ResponseEntity(
            ErrorResponseDto(
                timestamp = DateTimeUtils.getNow(),
                code = ErrorConstants.REQUEST_METHOD_INVALID,
                message = ex.message,
                trace = getServerMessage(ex),
                errors = null
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    /**
     * Handle when sending a request with unsupported media type
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    override fun handleHttpMediaTypeNotSupported(
        ex: HttpMediaTypeNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        log.warn(ex.message)

        return ResponseEntity(
            ErrorResponseDto(
                timestamp = DateTimeUtils.getNow(),
                code = ErrorConstants.MEDIA_TYPE_INVALID,
                message = ex.message,
                trace = getServerMessage(ex),
                errors = null
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    /**
     * Handle all invalid attributes
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val errors: List<ErrorDetail> =
            if (CollectionUtils.isEmpty(ex.bindingResult.fieldErrors)) {
                ex.bindingResult.allErrors.map { e ->
                    ErrorDetail(code = null, field = null, message = e.defaultMessage)
                }
            } else {
                ex.bindingResult.fieldErrors.map { e ->
                    ErrorDetail(code = null, field = e.field, message = e.defaultMessage)
                }
            }
        log.warn(errors.toString())

        return ResponseEntity(
            ErrorResponseDto(
                timestamp = DateTimeUtils.getNow(),
                code = ErrorConstants.VALIDATION_INVALID,
                message = "Method argument not valid",
                trace = getServerMessage(ex),
                errors = errors
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    /**
     * Handle when parameter is invalid (using for spring @Validated annotation)
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(ex: ConstraintViolationException): ResponseEntity<Any> {
        val errors =
            ex.constraintViolations.map { e ->
                ErrorDetail(code = null, field = null, message = e.message)
            }
        log.warn(errors.toString())

        return ResponseEntity(
            ErrorResponseDto(
                timestamp = DateTimeUtils.getNow(),
                code = ErrorConstants.VALIDATION_INVALID,
                message = ex.message,
                trace = getServerMessage(ex),
                errors = errors
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    /**
     * Handle when request missing parameter
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    override fun handleMissingServletRequestParameter(
        ex: MissingServletRequestParameterException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        return ResponseEntity(
            ErrorResponseDto(
                timestamp = DateTimeUtils.getNow(),
                code = ErrorConstants.VALIDATION_INVALID,
                message = ex.message,
                trace = getServerMessage(ex),
                errors = null
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    /**
     * Handle when sending a request with header invalid
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    override fun handleServletRequestBindingException(
        ex: ServletRequestBindingException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        return ResponseEntity(
            ErrorResponseDto(
                timestamp = DateTimeUtils.getNow(),
                code = ErrorConstants.REQUEST_HEADER_INVALID,
                message = ex.message,
                trace = getServerMessage(ex),
                errors = null
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    /**
     * Default handler Handle all system errors
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception::class)
    fun handleOthers(ex: Exception): ResponseEntity<Any> {
        log.error(ex.message)

        return ResponseEntity(
            ErrorResponseDto(
                timestamp = DateTimeUtils.getNow(),
                code = ErrorConstants.SYSTEM_ERROR,
                message = "System error",
                trace = getServerMessage(ex),
                errors = null
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    private fun getServerMessage(ex: Exception): String? {
        if (debug) {
            val sw = StringWriter()
            ex.printStackTrace(PrintWriter(sw))

            return sw.toString()
        }

        return null
    }
}
