package com.example.demo.article.service

import com.example.demo.article.mapper.toArticleEntity
import com.example.demo.article.mapper.toArticleListResponse
import com.example.demo.article.mapper.toSingleArticle
import com.example.demo.article.model.ArticleListResponse
import com.example.demo.article.model.ArticleWrapper
import com.example.demo.article.model.CreateArticleRequest
import com.example.demo.article.model.SingleArticleResponse
import com.example.demo.article.repository.ArticleRepository
import com.example.demo.config.JwtService
import com.example.demo.share.Constants.JWT_START_INDEX
import com.example.demo.tag.repository.TagRepository
import com.example.demo.users.entity.UserEntity
import com.example.demo.users.mapper.toAuthor
import com.example.demo.users.repoistory.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ArticleService(
    val articleRepository: ArticleRepository,
    val userRepository: UserRepository,
    val tagRepository: TagRepository,
    val jwtService: JwtService,
) {

    @Transactional
    fun saveNewArticle(articleRequest: CreateArticleRequest, token: String): ArticleWrapper<SingleArticleResponse> {
        val userEntity = findUserByToken(token)
        val entity = articleRequest.toArticleEntity(ownerUser = userEntity)
        val savedEntity = articleRepository.save(entity)
        return ArticleWrapper(
            savedEntity.toSingleArticle(
                user = userEntity.toAuthor()
            )
        )
    }

    fun getUserArticlesByUserName(username: String): ArticleListResponse {
        val userEntity = userRepository.findByUsername(username).get()
        return articleRepository.findAllByOwner(userEntity).get().toArticleListResponse()
    }

    fun getArticleBySlug(slug: String): ArticleWrapper<SingleArticleResponse>? {
        val articleEntity = articleRepository.findBySlug(slug.toLong()).get()
        return ArticleWrapper(
            article = articleEntity.toSingleArticle(articleEntity.owner.toAuthor())
        )
    }

    private fun findUserByToken(token: String): UserEntity {
        val email = jwtService.extractUserName(token.substring(JWT_START_INDEX))
        return userRepository.findByEmail(email.orEmpty()).get()
    }

    fun getTagArticles(tag: String): ArticleListResponse? {
        val tagEntity=tagRepository.findByText(tag).get()

        return tagEntity.articles.toArticleListResponse()
    }

}