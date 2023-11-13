package com.vndevteam.kotlinwebspringboot3.infrastructure.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.vndevteam.kotlinwebspringboot3.application.demo.DemoLoginDto
import com.vndevteam.kotlinwebspringboot3.domain.enums.UserRole
import com.vndevteam.kotlinwebspringboot3.infrastructure.security.AuthenticationUser
import com.vndevteam.kotlinwebspringboot3.infrastructure.util.JwtUtils
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.util.*
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class JwtAuthenticationFilter(
    private val jwtUtil: JwtUtils,
    private val authenticationManager: AuthenticationManager
) : UsernamePasswordAuthenticationFilter() {
    override fun attemptAuthentication(
        req: HttpServletRequest?,
        res: HttpServletResponse?
    ): Authentication {
        val credentials = ObjectMapper().readValue(req?.inputStream, DemoLoginDto::class.java)
        val auth = UsernamePasswordAuthenticationToken(
            credentials.email,
            credentials.password,
            Collections.singleton(SimpleGrantedAuthority(UserRole.USER.name))
        )
        return authenticationManager.authenticate(auth)
    }

    override fun successfulAuthentication(
        req: HttpServletRequest?,
        res: HttpServletResponse?,
        chain: FilterChain?,
        auth: Authentication?
    ) {
        val username = (auth?.principal as AuthenticationUser).username
        val token: String = jwtUtil.generateToken(username)
        res?.addHeader("Authorization", token)
        res?.addHeader("Access-Control-Expose-Headers", "Authorization")
    }

    override fun unsuccessfulAuthentication(
        req: HttpServletRequest?,
        res: HttpServletResponse?,
        failed: AuthenticationException?
    ) {
        val error = BadCredentialsError()
        res?.status = error.status
        res?.contentType = "application/json"
        res?.writer?.append(error.toString())
    }

    private data class BadCredentialsError(
        val timestamp: Long = Date().time,
        val status: Int = 401,
        val message: String = "Email or password incorrect"
    ) {
        override fun toString(): String {
            return ObjectMapper().writeValueAsString(this)
        }
    }
}
