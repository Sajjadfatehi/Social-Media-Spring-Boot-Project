package com.example.demo.article.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

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
    var ownerUsername: String,
)