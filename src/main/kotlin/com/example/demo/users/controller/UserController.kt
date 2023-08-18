package com.example.demo.users.controller

import com.example.demo.share.Constants
import com.example.demo.users.model.UserDtoWrapper
import com.example.demo.users.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("${Constants.BASE_URL}/profiles")
class UserController(val userService: UserService) {

    @GetMapping("/{username}")
    fun getUserByEmail(@PathVariable("username") username: String): ResponseEntity<UserDtoWrapper> {
        return ResponseEntity.ok(userService.findUserByEmail(username))
    }
}