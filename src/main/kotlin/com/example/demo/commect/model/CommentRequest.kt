package com.example.demo.commect.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CommentRequest(
    @JsonProperty("body")
    val body: String
)
