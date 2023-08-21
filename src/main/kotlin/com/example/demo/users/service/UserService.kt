package com.example.demo.users.service

import com.example.demo.users.mapper.toUserDTO
import com.example.demo.users.model.UserDtoWrapper
import com.example.demo.users.repoistory.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class UserService(val userRepository: UserRepository) {

    fun findUserByEmail(email: String): UserDtoWrapper {
        var userEntity = userRepository.findByUsername(email).getOrNull()
        if (userEntity == null) {
            userEntity = userRepository.findByEmail(email).orElseThrow {
                EntityNotFoundException("there is no user with username: $email")
            }
        }
        return UserDtoWrapper(userEntity!!.toUserDTO())
    }
}