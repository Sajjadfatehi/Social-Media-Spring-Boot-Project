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
    isBookmarked: Boolean,
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

//TODO: pass the correct value of isBookmark in every use case
//fun List<ArticleEntity>.toArticleListResponse(
//    isBookmarked: Boolean = false,
//    ) = ArticleListResponse(
//    articles = this.map {
//        it.toSingleArticle(
//            user = it.owner.toAuthor(),
//            isBookmarked = isBookmarked
//        )
//    },
//    articlesCount = this.size
//)

fun Map<ArticleEntity,Boolean>.toArticleListResponse(
) = ArticleListResponse(
    articles = this.map {
        it.key.toSingleArticle(
            user = it.key.owner.toAuthor(),
            isBookmarked = it.value
        )
    },
    articlesCount = this.size
)