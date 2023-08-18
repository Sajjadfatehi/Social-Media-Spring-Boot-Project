package com.example.demo.users.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UserDTO(
    @JsonProperty("username")
    val username: String? = null,
    @JsonProperty("bio")
    val bio: String? = null,
    @JsonProperty("email")
    val email: String? = null,
    @JsonProperty("following")
    val following: Boolean? = false,
    @JsonProperty("image")
    val image: String? = null,
)
