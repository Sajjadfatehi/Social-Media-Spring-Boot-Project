package com.example.demo.article.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ArticleListResponse(
    @JsonProperty("articles")
    val articles: List<SingleArticleResponse>,
    @JsonProperty("articlesCount")
    val articlesCount: Int
)