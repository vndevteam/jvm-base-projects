package com.vndevteam.kotlinwebspringboot3.domain.post

import com.vndevteam.kotlinwebspringboot3.application.post.PostEntity
import java.util.UUID
import org.springframework.stereotype.Repository

@Repository
class PostRepository {
    private val posts =
        listOf(
            PostEntity(id = UUID.randomUUID(), title = "Post 1", content = "Content 1"),
            PostEntity(id = UUID.randomUUID(), title = "Post 2", content = "Content 2"),
        )

    fun findAll(): List<PostEntity> = posts
}
