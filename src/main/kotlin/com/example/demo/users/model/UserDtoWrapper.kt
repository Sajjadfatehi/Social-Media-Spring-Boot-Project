package com.example.demo.users.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UserDtoWrapper(
    @JsonProperty("profile")
    val userDTO: UserDTO
)