package com.example.demo.users.service

import com.example.demo.users.entity.UserEntity
import com.example.demo.users.mapper.toUserDTO
import com.example.demo.users.model.UserDTO
import com.example.demo.users.model.UserDtoWrapper
import com.example.demo.users.repoistory.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository) {

    fun findUserByEmail(email: String): UserDtoWrapper {
        return UserDtoWrapper(userRepository.findByUsername(email).get().toUserDTO())
    }
}