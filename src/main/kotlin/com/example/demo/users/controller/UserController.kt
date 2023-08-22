package com.example.demo.users.controller

import com.example.demo.share.Constants
import com.example.demo.users.model.UserDtoWrapper
import com.example.demo.users.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("${Constants.BASE_URL}/profiles")
class UserController(val service: UserService) {

    @GetMapping("/{username}")
    fun getUserByEmail(
        @RequestHeader("Authorization") token: String,
        @PathVariable("username") username: String,
    ): ResponseEntity<UserDtoWrapper> {
        return ResponseEntity.ok(service.findUserByEmail(token,username))
    }

    @PostMapping("/{username}/follow")
    fun followUser(
        @RequestHeader("Authorization") token: String,
        @PathVariable("username") userName: String,
    ): ResponseEntity<UserDtoWrapper> {
        return ResponseEntity.ok(service.followUser(token, userName))
    }

    @DeleteMapping("/{username}/follow")
    fun unFollowUser(
        @RequestHeader("Authorization") token: String,
        @PathVariable("username") userName: String,
    ): ResponseEntity<UserDtoWrapper> {
        return ResponseEntity.ok(service.unFollowUser(token, userName))
    }

}