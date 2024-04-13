package com.vndevteam.kotlinwebspringboot3.infrastructure.exception.error

class ErrorCode {
    companion object {
        const val SYSTEM_ERROR = "100"
        const val REQUEST_METHOD_INVALID = "101"
        const val MEDIA_TYPE_INVALID = "102"
        const val VALIDATION_INVALID = "103"
        const val REQUEST_HEADER_INVALID = "104"
    }
}
