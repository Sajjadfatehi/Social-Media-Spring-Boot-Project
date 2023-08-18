package com.example.demo.commect.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CommentRequestWrapper(
    @JsonProperty("comment")
    val comment: CommentRequest
)
