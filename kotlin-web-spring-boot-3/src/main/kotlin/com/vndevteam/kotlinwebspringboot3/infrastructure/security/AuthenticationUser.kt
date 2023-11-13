package com.vndevteam.kotlinwebspringboot3.infrastructure.security

import com.vndevteam.kotlinwebspringboot3.application.demo.DemoLoginDto
import com.vndevteam.kotlinwebspringboot3.domain.enums.UserRole
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.User

class AuthenticationUser(user: DemoLoginDto) : User(
    user.email,
    user.password,
    true,
    true,
    true,
    true,
    AuthorityUtils.createAuthorityList(UserRole.USER.name)
) { }
