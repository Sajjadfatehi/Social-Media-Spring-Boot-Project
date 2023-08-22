package com.example.demo.users.mapper

import com.example.demo.article.model.Author
import com.example.demo.users.entity.UserEntity
import com.example.demo.users.model.UserDTO

fun UserEntity.toAuthor(isFollowing: Boolean = false) = Author(
    username = username,
    following = isFollowing,
    image = image.orEmpty()
)

//TODO: pass isFollowing to all user cases
fun UserEntity.toUserDTO(isFollowing:Boolean=false) = UserDTO(
    username = getRealUserName(),
    bio = getBio(),
    email = username,
    following = isFollowing,
    image = image?:"https://yt3.ggpht.com/-b-fXZSZ0hPw/AAAAAAAAAAI/AAAAAAAAAAA/mq4JpF46xq4/s28-c-k-no-mo-rj-c0xffffff/photo.jpg",
)//TODO: mock image