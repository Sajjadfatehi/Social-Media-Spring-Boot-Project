package com.example.demo.users.entity

import com.example.demo.article.entity.ArticleEntity
import com.example.demo.commect.entity.CommentEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


@Entity(name = "_user")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "username", unique = true)
    private val username: String,
    @Column(name = "password")
    private val password: String,
    @Column(name = "bio")
    private val bio: String? = null,
    @Column(name = "email", unique = true)
    private val email: String,
    @Column(name = "following")
    private val following: Boolean? = false,
    @Column(name = "image")
    val image: String? = null,

    @JsonIgnore
    @OneToMany(mappedBy = "owner", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val articles: MutableList<ArticleEntity> = mutableListOf(),

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val comments: MutableList<CommentEntity> = mutableListOf()
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority("my-admin"))
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    fun getRealUserName(): String {
        return username
    }

    fun getBio(): String {
        return bio.orEmpty()
    }

}
