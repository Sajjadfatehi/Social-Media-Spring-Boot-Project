package com.example.demo.users.mapper

import com.example.demo.article.model.Author
import com.example.demo.users.entity.UserEntity

fun UserEntity.toAuthor(isFollowing: Boolean = false) = Author(
    username = username,
    following = isFollowing,
    image = image.orEmpty()
)