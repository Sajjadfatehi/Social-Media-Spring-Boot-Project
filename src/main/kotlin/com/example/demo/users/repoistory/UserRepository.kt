package com.example.demo.users.repoistory

import com.example.demo.users.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<UserEntity, Long> {

    fun findByEmail(email: String): Optional<UserEntity>

//    fun findUsernameByEmail(email: String):Optional<String>

}