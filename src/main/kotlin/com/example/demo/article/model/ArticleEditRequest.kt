package com.example.demo.article.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ArticleEditRequest(
    @JsonProperty("body")
    val body: String
)