package com.example.demo.tag.mapper

import com.example.demo.tag.entity.TagEntity

fun String.toTagEntity() = TagEntity(
    text = this
)
