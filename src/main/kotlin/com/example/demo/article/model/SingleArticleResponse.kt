package com.example.demo.article.model

import com.fasterxml.jackson.annotation.JsonProperty

data class SingleArticleResponse(
    @JsonProperty("author")
    val user: Author,
    @JsonProperty("body")
    val body: String,
    @JsonProperty("createdAt")
    val createdAt: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("favorited")
    val isBookmarked: Boolean,
    @JsonProperty("favoritesCount")
    val favoritesCount: Int,
    @JsonProperty("slug")
    val slug: String,
    @JsonProperty("tagList")
    val tagList: List<String>,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("updatedAt")
    val updatedAt: String,
)

data class Author(
    @JsonProperty("following")
    val following: Boolean,
    @JsonProperty("image")
    val image: String,
    @JsonProperty("username")
    val username: String
)