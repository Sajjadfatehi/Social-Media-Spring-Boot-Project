package com.example.demo.users.entity

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


@Entity(name = "_user")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long =0,
    @Column(name = "username", unique = true)
    private val username: String,
    @Column(name = "password")
    private val password: String,
    @Column(name = "bio")
    private val bio: String? = null,
    @Column(name = "email", unique = true)
    private val email: String,
    @Column(name = "token")
    private val token: String? = null,
    @Column(name = "following")
    private val following: Boolean? = false,
    @Column(name = "image")
    val image: String? = null
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
}
