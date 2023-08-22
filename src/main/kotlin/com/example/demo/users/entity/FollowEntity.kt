package com.example.demo.users.entity

import jakarta.persistence.*

@Entity(name = "follow")
data class FollowEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    val follower: UserEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    val following: UserEntity
)