package com.example.demo.users.entity

import com.example.demo.article.entity.ArticleEntity
import jakarta.persistence.*

@Entity(name = "bookmark")
data class BookmarkEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val user: UserEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_slug", referencedColumnName = "slug")
    val article: ArticleEntity
)