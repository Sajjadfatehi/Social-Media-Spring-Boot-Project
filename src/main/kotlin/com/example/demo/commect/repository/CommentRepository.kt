package com.example.demo.commect.repository

import com.example.demo.article.entity.ArticleEntity
import com.example.demo.commect.entity.CommentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CommentRepository : JpaRepository<CommentEntity, Long> {
    fun findAllByArticle(articleEntity: ArticleEntity): Optional<List<CommentEntity>>
}