package com.example.demo.article.entity

import com.example.demo.users.entity.UserEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity(name = "article")
data class ArticleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var slug: Long = 0,
    var title: String,
    val createdAt: String? = null,
    val updateAt: String? = null,
    var body: String,
    var favoriteCount: Int=0,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    var owner: UserEntity
)