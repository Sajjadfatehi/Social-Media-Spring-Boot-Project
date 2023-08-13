package com.example.demo.article.mapper

import com.example.demo.article.entity.ArticleEntity
import com.example.demo.article.model.Author
import com.example.demo.article.model.CreateArticleRequest
import com.example.demo.article.model.SingleArticleResponse

fun CreateArticleRequest.toArticleEntity(ownerUserName: String = "") = ArticleEntity(
    body = body,
    title = title,
    createdAt = System.currentTimeMillis().toString(),
    updateAt = System.currentTimeMillis().toString(),
    ownerUsername = ownerUserName
)

fun ArticleEntity.toSingleArticle(
    user: Author,
    isBookmarked:Boolean=false,
    tags:List<String> = mutableListOf()
) = SingleArticleResponse(
    user=user,
    body=body,
    title=title,
    createdAt = createdAt.orEmpty(),
    updatedAt = updateAt.orEmpty(),
    description = "",
    isBookmarked = isBookmarked,
    favoritesCount = favoriteCount,
    slug = slug.toString(),
    tagList = tags
)