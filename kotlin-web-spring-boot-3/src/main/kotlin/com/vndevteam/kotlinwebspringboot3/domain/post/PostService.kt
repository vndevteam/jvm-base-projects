package com.vndevteam.kotlinwebspringboot3.domain.post

import com.vndevteam.kotlinwebspringboot3.application.post.PostEntity
import org.springframework.stereotype.Service

@Service
class PostService(private val postRepository: PostRepository) {
    fun findAll(): List<PostEntity> = postRepository.findAll()
}
