package com.vndevteam.kotlinwebspringboot3.domain.user

import com.vndevteam.kotlinwebspringboot3.application.user.UserEntity
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
    fun createUser(user: UserEntity): UserEntity? {
        val found = userRepository.findByEmail(user.email)
        return if (found == null) {
            userRepository.save(user)
            user
        } else null
    }

    fun findByUUID(uuid: UUID): UserEntity? = userRepository.findByUUID(uuid)

    fun findAll(): List<UserEntity> = userRepository.findAll().toList()

    fun deleteByUUID(uuid: UUID): Boolean = userRepository.deleteByUUID(uuid)
}
