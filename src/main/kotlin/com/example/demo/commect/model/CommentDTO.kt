package com.example.demo.commect.model

import com.example.demo.users.model.UserDTO
import com.fasterxml.jackson.annotation.JsonProperty

data class CommentDTO(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("body")
    val body: String,
    @JsonProperty("createdAt")
    val createdAt: String,
    @JsonProperty("author")
    val user: UserDTO
)
