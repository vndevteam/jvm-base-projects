package com.vndevteam.kotlinwebspringboot3.domain.auth

import com.vndevteam.kotlinwebspringboot3.domain.user.UserRepository
import com.vndevteam.kotlinwebspringboot3.util.toUserDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails =
        userRepository.findByEmail(username)?.toUserDetails()
            ?: throw UsernameNotFoundException("Not found!")
}
