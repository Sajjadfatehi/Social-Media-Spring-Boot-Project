package com.example.demo.article.model

import com.fasterxml.jackson.annotation.JsonProperty

data class EditArticleRequestWrapper(
    @JsonProperty("article")
    val articleEditRequest: ArticleEditRequest,
)