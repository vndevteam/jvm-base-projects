package com.vndevteam.kotlinwebspringboot3.application.user

import com.vndevteam.kotlinwebspringboot3.domain.user.UserService
import com.vndevteam.kotlinwebspringboot3.util.toUserEntity
import com.vndevteam.kotlinwebspringboot3.util.toUserResDto
import java.util.UUID
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/users")
class UserRestController(private val userService: UserService) {
    @PostMapping
    fun create(@RequestBody userReqDto: UserReqDto): UserResDto =
        userService.createUser(userReqDto.toUserEntity())?.toUserResDto()
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot create user.")

    @GetMapping fun listAll(): List<UserResDto> = userService.findAll().map { it.toUserResDto() }

    @GetMapping("/{uuid}")
    fun findByUUID(@PathVariable uuid: UUID): UserResDto =
        userService.findByUUID(uuid)?.toUserResDto()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")

    @DeleteMapping("/{uuid}")
    fun deleteByUUID(@PathVariable uuid: UUID): ResponseEntity<Boolean> {
        val success = userService.deleteByUUID(uuid)

        return if (success) ResponseEntity.noContent().build()
        else throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")
    }
}
