package com.vndevteam.kotlinwebspringboot3.infrastructure.security.config

import com.vndevteam.kotlinwebspringboot3.infrastructure.security.filter.JwtAuthenticationFilter
import com.vndevteam.kotlinwebspringboot3.infrastructure.security.filter.JwtAuthorizationFilter
import com.vndevteam.kotlinwebspringboot3.infrastructure.security.service.UserSecurityService
import com.vndevteam.kotlinwebspringboot3.infrastructure.util.JwtUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig{
    @Autowired
    lateinit var jwtUtil: JwtUtils

    @Autowired
    lateinit var userSecurityService: UserSecurityService

    private fun authManager(http: HttpSecurity): AuthenticationManager {
        val authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder::class.java)
        authenticationManagerBuilder.userDetailsService(userSecurityService)
        return authenticationManagerBuilder.build()
    }

    @Bean
    open fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { request ->
                request
//                    .requestMatchers(HttpMethod.POST, "/demo/login").permitAll()
                    .anyRequest().authenticated()
            }.csrf { csrf -> csrf.disable() }.cors { cors -> cors.disable()
            }.sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
//            .addFilter(JwtAuthenticationFilter(jwtUtil, authManager(http)))
//            .addFilter(JwtAuthorizationFilter(jwtUtil, userSecurityService, authManager(http)))
            .authenticationManager(authManager(http))

        return http.build()
    }
}
