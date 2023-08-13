package com.example.demo.auth.controller

import com.example.demo.auth.model.*
import com.example.demo.auth.service.AuthService
import com.example.demo.share.Constants.BASE_URL
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("$BASE_URL/users")
class AuthController (val service: AuthService){


    @PostMapping
    fun register(
        @RequestBody request: RegisterRequestWrapper
    ): ResponseEntity<AuthResponseWrapper?>? {
        return ResponseEntity.ok(service.register(request.registerRequest))
    }

    @PostMapping("/login")
    fun authenticate(
        @RequestBody request: LoginRequestWrapper
    ): ResponseEntity<AuthResponseWrapper?>? {
        return ResponseEntity.ok(service.authenticate(request.loginRequest))
    }

}