package com.example.demo.article.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ArticleWrapper<T>(
    @JsonProperty("article")
    val article: T
)
