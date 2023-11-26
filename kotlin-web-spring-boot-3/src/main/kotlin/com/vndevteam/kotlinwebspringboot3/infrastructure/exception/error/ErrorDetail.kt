package com.vndevteam.kotlinwebspringboot3.infrastructure.exception.error

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class ErrorDetail(var code: String?, var field: String?, var message: String?)
