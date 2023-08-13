package com.example.demo.article.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateArticleRequest(
    @JsonProperty("body")
    val body: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("tagList")
    val tagList: List<String>,
    @JsonProperty("title")
    val title: String
)