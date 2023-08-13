package com.example.demo.article.service

import com.example.demo.article.mapper.toArticleEntity
import com.example.demo.article.mapper.toSingleArticle
import com.example.demo.article.model.ArticleWrapper
import com.example.demo.article.model.CreateArticleRequest
import com.example.demo.article.model.SingleArticleResponse
import com.example.demo.article.repository.ArticleRepository
import com.example.demo.config.JwtService
import com.example.demo.users.mapper.toAuthor
import com.example.demo.users.repoistory.UserRepository
import org.springframework.stereotype.Service

@Service
class ArticleService(
    val articleRepository: ArticleRepository,
    val userRepository: UserRepository,
    val jwtService: JwtService
) {

    fun saveNewArticle(articleRequest: CreateArticleRequest, token: String): ArticleWrapper<SingleArticleResponse> {
        val email = jwtService.extractUserName(token.substring(7))
        val userEntity = userRepository.findByEmail(email.orEmpty()).get()
        val entity = articleRequest.toArticleEntity(ownerUserName = userEntity.username)
        val savedEntity = articleRepository.save(entity)
        return ArticleWrapper(
            savedEntity.toSingleArticle(
                user = userEntity.toAuthor()
            )
        )
    }
}