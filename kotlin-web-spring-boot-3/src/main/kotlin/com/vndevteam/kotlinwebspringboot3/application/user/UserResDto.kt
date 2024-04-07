package com.vndevteam.kotlinwebspringboot3.application.user

import java.util.UUID

data class UserResDto(
    val uuid: UUID,
    val email: String,
)
