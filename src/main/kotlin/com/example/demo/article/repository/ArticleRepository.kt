package com.example.demo.article.repository

import com.example.demo.article.entity.ArticleEntity
import com.example.demo.users.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface ArticleRepository : JpaRepository<ArticleEntity, Long> {

    fun findAllByOwner(userEntity: UserEntity): Optional<List<ArticleEntity>>

    @Query("select '*' from article where owner.username = :userName")
    fun findAllByOwnerName(@Param("userName") userName: String): Optional<List<ArticleEntity>>

    fun findBySlug(slug: Long): Optional<ArticleEntity>
}