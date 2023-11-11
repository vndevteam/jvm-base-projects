package com.vndevteam.kotlinwebspringboot3.infrastructure.security.service

import com.vndevteam.kotlinwebspringboot3.application.demo.DemoLoginDto
import com.vndevteam.kotlinwebspringboot3.infrastructure.security.AuthenticationUser
import java.util.*
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserSecurityService: UserDetailsService {
    override fun loadUserByUsername(userId: String): UserDetails {
        return loadUser(userId)
    }

    private fun loadUser(userId: String): User {
        // TODO search user in DB
        val demoLoginDto = DemoLoginDto().apply {
            "a@example.com"
            "123456"
        }
        val user = Optional.of(demoLoginDto)

        // Not found
        if (!user.isPresent) {
            throw UsernameNotFoundException("Username not found")
        }

        // AuthenticationRequest
        return AuthenticationUser(user.get())
    }
}
