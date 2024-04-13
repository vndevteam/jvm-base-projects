package com.vndevteam.kotlinwebspringboot3.domain.auth

import com.vndevteam.kotlinwebspringboot3.application.auth.AuthReqDto
import com.vndevteam.kotlinwebspringboot3.application.auth.AuthResDto
import com.vndevteam.kotlinwebspringboot3.infrastructure.config.JwtProperties
import com.vndevteam.kotlinwebspringboot3.util.DateTimeUtils
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties,
    private val refreshTokenRepository: RefreshTokenRepository,
) {
    fun authentication(authenticationRequest: AuthReqDto): AuthResDto {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authenticationRequest.email,
                authenticationRequest.password,
            )
        )

        val user = userDetailsService.loadUserByUsername(authenticationRequest.email)

        return AuthResDto(
            jwtToken = createJwtToken(user),
            refreshToken = createRefreshToken(user),
        )
    }

    fun refreshAccessToken(refreshToken: String): String? {
        val extractedEmail = tokenService.extractEmail(refreshToken)
        return extractedEmail?.let { email ->
            val currentUserDetails = userDetailsService.loadUserByUsername(email)
            val refreshTokenUserDetails =
                refreshTokenRepository.findUserDetailsByToken(refreshToken)
            if (
                !tokenService.isExpired(refreshToken) &&
                    refreshTokenUserDetails?.username == currentUserDetails.username
            )
                createJwtToken(currentUserDetails)
            else null
        }
    }

    private fun createJwtToken(user: UserDetails) =
        tokenService.generate(
            userDetails = user,
            expirationDate =
                DateTimeUtils.getDateNow(
                    DateTimeUtils.getNow().plusMinutes(jwtProperties.jwtTokenExpiration)
                )
        )

    private fun createRefreshToken(user: UserDetails) =
        tokenService.generate(
            userDetails = user,
            expirationDate =
                DateTimeUtils.getDateNow(
                    DateTimeUtils.getNow().plusMinutes(jwtProperties.refreshTokenExpiration)
                )
        )
}
