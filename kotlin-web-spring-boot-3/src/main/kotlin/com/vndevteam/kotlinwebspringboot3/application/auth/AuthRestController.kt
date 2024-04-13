package com.vndevteam.kotlinwebspringboot3.application.auth

import com.vndevteam.kotlinwebspringboot3.domain.auth.AuthService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/login")
    fun authenticate(@RequestBody authenticationReqDto: AuthReqDto): AuthResDto =
        authService.authentication(authenticationReqDto)

    @PostMapping("/refresh")
    fun refreshAccessToken(@RequestBody request: RefreshTokenReqDto): TokenResDto =
        authService.refreshAccessToken(request.jwtToken)?.toTokenResDto()
            ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid refresh token.")

    private fun String.toTokenResDto(): TokenResDto = TokenResDto(jwtToken = this)
}
