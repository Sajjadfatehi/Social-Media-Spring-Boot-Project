package com.example.demo.article.mapper

import com.example.demo.article.entity.ArticleEntity
import com.example.demo.article.model.ArticleListResponse
import com.example.demo.article.model.Author
import com.example.demo.article.model.CreateArticleRequest
import com.example.demo.article.model.SingleArticleResponse
import com.example.demo.tag.mapper.toTagEntity
import com.example.demo.users.entity.UserEntity
import com.example.demo.users.mapper.toAuthor

fun CreateArticleRequest.toArticleEntity(ownerUser: UserEntity) = ArticleEntity(
    body = body,
    title = title,
    createdAt = System.currentTimeMillis().toString(),
    updateAt = System.currentTimeMillis().toString(),
    owner = ownerUser,
    tags = tagList.map { it.toTagEntity() }.toMutableList()
)
//TODO: pass the correct value of isBookmark in every use case
fun ArticleEntity.toSingleArticle(
    user: Author,
    isBookmarked: Boolean = false,
) = SingleArticleResponse(
    user = user,
    body = body,
    title = title,
    createdAt = createdAt.orEmpty(),
    updatedAt = updateAt.orEmpty(),
    description = "",
    isBookmarked = isBookmarked,
    favoritesCount = favoriteCount,
    slug = slug.toString(),
    tagList = tags.map { it.text }
)

fun List<ArticleEntity>.toArticleListResponse(
//    user: Author,
    isBookmarked: Boolean = false,

    ) = ArticleListResponse(
    articles = this.map {
        it.toSingleArticle(
//            user = user
            user = it.owner.toAuthor()
        )
    },
    articlesCount = this.size
)