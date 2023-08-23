package com.example.demo.article.service

import com.example.demo.article.entity.ArticleEntity
import com.example.demo.article.mapper.toArticleEntity
import com.example.demo.article.mapper.toArticleListResponse
import com.example.demo.article.mapper.toSingleArticle
import com.example.demo.article.model.*
import com.example.demo.article.repository.ArticleRepository
import com.example.demo.config.JwtService
import com.example.demo.share.Constants.JWT_START_INDEX
import com.example.demo.tag.entity.TagEntity
import com.example.demo.tag.repository.TagRepository
import com.example.demo.users.entity.BookmarkEntity
import com.example.demo.users.entity.UserEntity
import com.example.demo.users.mapper.toAuthor
import com.example.demo.users.repoistory.BookmarkRepository
import com.example.demo.users.repoistory.UserRepository
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse
import kotlin.jvm.optionals.getOrNull

@Service
@Transactional
class ArticleService(
    val articleRepository: ArticleRepository,
    val userRepository: UserRepository,
    val tagRepository: TagRepository,
    val jwtService: JwtService,
    val bookmarkRepository: BookmarkRepository
) {

    @Transactional
    fun saveNewArticle(articleRequest: CreateArticleRequest, token: String): ArticleWrapper<SingleArticleResponse> {
        val userEntity = findUserByToken(token).also {
            if (it == null) throw EntityNotFoundException("author id is invalid. user not found")
        }
        val entity = articleRequest.toArticleEntity(ownerUser = userEntity!!)

        /** doing that for avoid adding tag with existing text in db. add exact those instance */
        val tagsToAdd = mutableListOf<TagEntity>()
        for (tag in entity.tags) {
            val existingTag = tagRepository.findByText(tag.text).getOrNull()
            if (existingTag != null) {
                tagsToAdd.add(existingTag)
            } else {
                tagsToAdd.add(tag)
            }
        }
        entity.tags.clear()
        entity.tags.addAll(tagsToAdd)

        val savedEntity = articleRepository.save(entity)
        return ArticleWrapper(
            savedEntity.toSingleArticle(
                user = userEntity.toAuthor()
            )
        )
    }

    fun getUserArticlesByUserName(username: String): ArticleListResponse {
        var userEntity = userRepository.findByUsername(username).getOrNull()
        if (userEntity == null) {
            userEntity = userRepository.findByEmail(username).orElseThrow {
                EntityNotFoundException("there is no user with username: $username")
            }
        }
        return userEntity!!.articles.toArticleListResponse()
//        return articleRepository.findAllByOwner(userEntity!!).get().toArticleListResponse()
    }

    fun getArticleBySlug(slug: String): ArticleWrapper<SingleArticleResponse>? {
        val articleEntity = articleRepository.findBySlug(slug.toLong()).get()
        return ArticleWrapper(
            article = articleEntity.toSingleArticle(articleEntity.owner.toAuthor())
        )
    }

    private fun findUserByToken(token: String): UserEntity? {
        val email = jwtService.extractUserName(token.substring(JWT_START_INDEX))
        return userRepository.findByEmail(email.orEmpty()).getOrNull()
    }

    fun getTagArticles(tag: String): ArticleListResponse? {
        val tagEntity = tagRepository.findByText(tag).get()

        return tagEntity.articles.toArticleListResponse()
    }

    fun editArticle(body: EditArticleRequestWrapper, slug: String): ArticleWrapper<SingleArticleResponse>? {
        val existingArticle = articleRepository.findById(slug.toLong())
            .orElseThrow { EntityNotFoundException("Article not found with slug: $slug") }

        existingArticle.body = body.articleEditRequest.body
        val updatedEntity = articleRepository.save(existingArticle)
        return ArticleWrapper(updatedEntity.toSingleArticle(updatedEntity.owner.toAuthor()))
    }

    @Transactional
    fun deleteArticle(slug: String) {
        val articleToDelete = articleRepository.findBySlug(slug.toLong()).orElseThrow {
            throw EntityNotFoundException("Article not found with title: $slug")
        }

        articleToDelete.tags.clear()
        articleRepository.delete(articleToDelete)
    }

    fun bookmarkArticle(token: String, slug: String): ArticleWrapper<SingleArticleResponse>? {
        val userEntity = findUserByToken(token)
        val articleEntity = articleRepository.findBySlug(slug.toLong()).getOrNull()

        if (userEntity != null && articleEntity != null) {
            if (alreadyBookmarked(userEntity, articleEntity)) {
                return ArticleWrapper(
                    articleEntity.toSingleArticle(
                        user = articleEntity.owner.toAuthor(), isBookmarked = true
                    )
                )
            }

            val bookmark = BookmarkEntity(user = userEntity, article = articleEntity)
            userEntity.bookmarks.add(bookmark)
            userRepository.save(userEntity)
        } else {
            throw EntityNotFoundException("user or article not found")
        }
        return ArticleWrapper(
            articleEntity.toSingleArticle(
                user = articleEntity.owner.toAuthor(), isBookmarked = true
            )
        )
    }

    private fun alreadyBookmarked(
        userEntity: UserEntity,
        articleEntity: ArticleEntity?,
    ): Boolean {
        return userEntity.bookmarks.any {
            it.user == userEntity && it.article == articleEntity
        }
    }

    fun removeFromBookmarks(token: String, slug: String): ArticleWrapper<SingleArticleResponse>? {
        val userEntity = findUserByToken(token)
        val articleEntity = articleRepository.findBySlug(slug.toLong()).getOrNull()

        if (userEntity != null && articleEntity != null) {
            val bookmarkToRemove = userEntity.bookmarks.find { it.article == articleEntity }

            if (bookmarkToRemove != null) {
                userEntity.bookmarks.remove(bookmarkToRemove)
                userRepository.save(userEntity)
                articleEntity.bookmarks.remove(bookmarkToRemove)
                articleRepository.save(articleEntity)
                bookmarkRepository.delete(bookmarkToRemove)
            }

        } else {
            throw EntityNotFoundException("user or article not found")
        }
        return ArticleWrapper(
            articleEntity.toSingleArticle(
                user = articleEntity.owner.toAuthor(), isBookmarked = false
            )
        )
    }

    fun getBookmarkedArticles(username: String): ArticleListResponse? {
        var userEntity = userRepository.findByUsername(username).getOrNull()
        if (userEntity == null) {
            userEntity = userRepository.findByEmail(username).orElseThrow {
                EntityNotFoundException("there is no user with username: $username")
            }
        }

        return userEntity!!.bookmarks.map { it.article }.toArticleListResponse()
    }


}