package com.example.demo.tag.entity

import com.example.demo.article.entity.ArticleEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity(name = "tag")
data class TagEntity(

    @Id
    val text: String,

    @JsonIgnore
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    val articles: MutableList<ArticleEntity> = mutableListOf(),
)