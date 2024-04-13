package com.vndevteam.kotlinwebspringboot3.application.user

import com.vndevteam.kotlinwebspringboot3.common.enums.UserRole
import java.util.UUID

data class UserEntity(val id: UUID, val email: String, val password: String, val role: UserRole)
