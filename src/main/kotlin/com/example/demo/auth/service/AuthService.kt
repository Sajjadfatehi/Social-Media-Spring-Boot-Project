package com.example.demo.auth.service

import com.example.demo.auth.model.AuthResponse
import com.example.demo.auth.model.AuthResponseWrapper
import com.example.demo.auth.model.LoginRequest
import com.example.demo.auth.model.RegisterRequest
import com.example.demo.config.JwtService
import com.example.demo.users.entity.UserEntity
import com.example.demo.users.repoistory.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class AuthService(
    val repository: UserRepository,
    val passwordEncoder: PasswordEncoder,
    val jwtService: JwtService,
    val authenticationManager: AuthenticationManager
) {


    fun register(request: RegisterRequest): AuthResponseWrapper? {
        val user = UserEntity(
            username = request.username,
            email = request.email,
            password = passwordEncoder.encode(request.password)
        )
        repository.save(user)
        val jwtToken = jwtService.generateToken(user)
        return AuthResponseWrapper(
            AuthResponse(
                token = jwtToken,
                email = request.username,
                username = user.getRealUserName()
            )
        )
    }

    fun authenticate(request: LoginRequest): AuthResponseWrapper? {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.password
            )
        )

        val user = repository.findByEmail(request.email)
            .orElseThrow()
        val jwtToken = jwtService.generateToken(user)
        return AuthResponseWrapper(
            AuthResponse(
                token = jwtToken,
                email = user.username,
                username = user.getRealUserName()
            )
        )
    }

}