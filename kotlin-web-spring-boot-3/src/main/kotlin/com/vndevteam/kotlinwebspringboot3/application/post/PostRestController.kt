package com.vndevteam.kotlinwebspringboot3.application.post

import com.vndevteam.kotlinwebspringboot3.domain.post.PostService
import com.vndevteam.kotlinwebspringboot3.util.toPostResDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/posts")
class PostRestController(private val postService: PostService) {
    @GetMapping fun listAll(): List<PostResDto> = postService.findAll().map { it.toPostResDto() }
}
