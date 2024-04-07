package com.vndevteam.kotlinwebspringboot3.common.error

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

class ErrorResponseDto(
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS") var timestamp: LocalDateTime?,
    var code: String?,
    var message: String?,
    var trace: String?,
    var errors: List<ErrorDetail>?
)
