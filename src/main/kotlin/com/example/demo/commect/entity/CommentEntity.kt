package com.example.demo.commect.entity

import com.example.demo.article.entity.ArticleEntity
import com.example.demo.users.entity.UserEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity(name = "comment")
data class CommentEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long=0,
    val body: String,
    val createdAt: String,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_slug", referencedColumnName = "slug")
    val article: ArticleEntity,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    val author: UserEntity

)
