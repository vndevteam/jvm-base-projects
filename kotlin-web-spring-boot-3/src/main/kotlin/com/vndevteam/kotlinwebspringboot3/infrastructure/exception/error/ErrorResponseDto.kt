package com.vndevteam.kotlinwebspringboot3.infrastructure.exception.error

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.ZonedDateTime

class ErrorResponseDto(
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX") var timestamp: ZonedDateTime?,
    var code: String?,
    var message: String?,
    var errors: List<ErrorDetail>?,
    var trace: String?
)
