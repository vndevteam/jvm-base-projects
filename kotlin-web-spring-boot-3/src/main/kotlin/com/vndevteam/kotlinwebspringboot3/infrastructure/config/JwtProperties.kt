package com.vndevteam.kotlinwebspringboot3.infrastructure.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("jwt")
data class JwtProperties(
    val key: String,
    val jwtTokenExpiration: Long,
    val refreshTokenExpiration: Long,
)
