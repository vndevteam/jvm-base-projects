package com.vndevteam.kotlinwebspringboot3.domain.user

import com.vndevteam.kotlinwebspringboot3.application.user.UserEntity
import com.vndevteam.kotlinwebspringboot3.common.enums.UserRole
import java.util.UUID
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository

@Repository
class UserRepository(private val encoder: PasswordEncoder) {

    private val users =
        mutableSetOf(
            UserEntity(
                id = UUID.randomUUID(),
                email = "email-1@example.com",
                password = encoder.encode("pass1"),
                role = UserRole.USER,
            ),
            UserEntity(
                id = UUID.randomUUID(),
                email = "email-2@example.com",
                password = encoder.encode("pass2"),
                role = UserRole.ADMIN,
            ),
            UserEntity(
                id = UUID.randomUUID(),
                email = "email-3@example.com",
                password = encoder.encode("pass3"),
                role = UserRole.USER,
            ),
        )

    fun save(user: UserEntity): Boolean {
        val updated = user.copy(password = encoder.encode(user.password))

        return users.add(updated)
    }

    fun findByEmail(email: String): UserEntity? = users.firstOrNull { it.email == email }

    fun findAll(): Set<UserEntity> = users

    fun findByUUID(uuid: UUID): UserEntity? = users.firstOrNull { it.id == uuid }

    fun deleteByUUID(uuid: UUID): Boolean {
        val foundUser = findByUUID(uuid)
        return foundUser?.let { users.removeIf { it.id == uuid } } ?: false
    }
}
