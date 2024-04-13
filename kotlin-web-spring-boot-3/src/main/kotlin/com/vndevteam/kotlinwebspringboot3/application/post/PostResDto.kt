package com.vndevteam.kotlinwebspringboot3.application.post

import java.util.UUID

data class PostResDto(
    val id: UUID,
    val title: String,
    val content: String,
)
