package com.example.demo.commect.service

import com.example.demo.article.repository.ArticleRepository
import com.example.demo.commect.entity.CommentEntity
import com.example.demo.commect.mapper.toCommentDTO
import com.example.demo.commect.model.ArticleCommentsWrapper
import com.example.demo.commect.model.CommentRequestWrapper
import com.example.demo.commect.repository.CommentRepository
import com.example.demo.config.JwtService
import com.example.demo.share.Constants.JWT_START_INDEX
import com.example.demo.users.repoistory.UserRepository
import org.springframework.stereotype.Service

@Service
class CommentService(
    val commentRepository: CommentRepository,
    val articleRepository: ArticleRepository,
    val userRepository: UserRepository,
    val jwtService: JwtService,
) {

    fun saveComment(
        token: String,
        slug: String,
        commentRequestWrapper: CommentRequestWrapper,
    ) {
        val email = jwtService.extractUserName(token.substring(JWT_START_INDEX))
        val author = userRepository.findByEmail(email.orEmpty()).get()

        val relatedArticle = articleRepository.findBySlug(slug = slug.toLong()).get()

        commentRepository.save(
            CommentEntity(
                body = commentRequestWrapper.comment.body,
                createdAt = System.currentTimeMillis().toString(),
                article = relatedArticle,
                author = author
            )
        )
    }

    fun getArticleComments(slug: String): ArticleCommentsWrapper {
        val relatedArticle = articleRepository.findBySlug(slug = slug.toLong()).get()
        val comments = commentRepository.findAllByArticle(relatedArticle).get()
        return ArticleCommentsWrapper(comments.map { it.toCommentDTO() })
    }
}