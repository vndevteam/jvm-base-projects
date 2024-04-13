package com.vndevteam.kotlinwebspringboot3.application.auth

data class AuthResDto(
    val jwtToken: String,
    val refreshToken: String,
)
