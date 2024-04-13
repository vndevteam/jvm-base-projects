package com.vndevteam.kotlinwebspringboot3.util

import com.vndevteam.kotlinwebspringboot3.application.post.PostEntity
import com.vndevteam.kotlinwebspringboot3.application.post.PostResDto
import com.vndevteam.kotlinwebspringboot3.application.user.UserEntity
import com.vndevteam.kotlinwebspringboot3.application.user.UserReqDto
import com.vndevteam.kotlinwebspringboot3.application.user.UserResDto
import com.vndevteam.kotlinwebspringboot3.common.enums.UserRole
import java.util.UUID
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails

fun PostEntity.toPostResDto(): PostResDto =
    PostResDto(
        id = this.id,
        title = this.title,
        content = this.content,
    )

fun UserEntity.toUserResDto(): UserResDto =
    UserResDto(
        uuid = this.id,
        email = this.email,
    )

fun UserReqDto.toUserEntity(): UserEntity =
    UserEntity(
        id = UUID.randomUUID(),
        email = this.email,
        password = this.password,
        role = UserRole.USER,
    )

fun UserEntity.toUserDetails(): UserDetails =
    User.builder().username(this.email).password(this.password).roles(this.role.name).build()
