package com.example.demo.commect.mapper

import com.example.demo.commect.entity.CommentEntity
import com.example.demo.commect.model.CommentDTO
import com.example.demo.users.mapper.toUserDTO

fun CommentEntity.toCommentDTO() = CommentDTO(
    id = id.toString(),
    body = body,
    createdAt = createdAt,
    user = author.toUserDTO()
)