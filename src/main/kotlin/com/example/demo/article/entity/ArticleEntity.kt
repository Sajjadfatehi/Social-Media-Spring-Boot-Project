package com.example.demo.article.entity

import com.example.demo.commect.entity.CommentEntity
import com.example.demo.tag.entity.TagEntity
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
    var owner: UserEntity,

    @JsonIgnore
    @OneToMany(mappedBy = "article", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val comments: MutableList<CommentEntity> = mutableListOf(),

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "article_tag",
        joinColumns = [JoinColumn(name = "article_slug", referencedColumnName = "slug")],
        inverseJoinColumns = [JoinColumn(name = "tag_text", referencedColumnName = "text")]
    )
    val tags: MutableList<TagEntity> = mutableListOf()
)