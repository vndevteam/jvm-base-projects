package com.vndevteam.kotlinwebspringboot3.application.post

import java.util.UUID

data class PostEntity(
    val id: UUID,
    val title: String,
    val content: String,
)
