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
    @Column(name = "image")
    val image: String? = null,

    @JsonIgnore
    @OneToMany(mappedBy = "owner", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val articles: MutableList<ArticleEntity> = mutableListOf(),

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val comments: MutableList<CommentEntity> = mutableListOf(),


    @JsonIgnore
    @OneToMany(mappedBy = "follower", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val followingUsers: MutableList<FollowEntity> = mutableListOf(),

    @JsonIgnore
    @OneToMany(mappedBy = "following", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val followers: MutableList<FollowEntity> = mutableListOf(),

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

    fun follow(userToFollow: UserEntity) {
        if (!isFollowing(userToFollow)) {
            val followEntity = FollowEntity(follower = this, following = userToFollow)
            followingUsers.add(followEntity)
        }
    }

    fun unfollow(userToUnfollow: UserEntity) {
        val followToRemove = followingUsers.firstOrNull() { it.following == userToUnfollow }
        if (followToRemove != null) {
            followingUsers.remove(followToRemove)
            userToUnfollow.followers.remove(followToRemove)
        }
    }

    fun isFollowing(user: UserEntity): Boolean {
        return followingUsers.any { it.following == user }
    }

}
