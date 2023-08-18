package com.example.demo.commect.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ArticleCommentsWrapper(
    @JsonProperty("comments")
    val commentDTOS: List<CommentDTO>,
)
