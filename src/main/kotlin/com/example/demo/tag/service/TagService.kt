package com.example.demo.tag.service

import com.example.demo.tag.model.AllTagsResponse
import com.example.demo.tag.repository.TagRepository
import org.springframework.stereotype.Service

@Service
class TagService(private val tagRepository: TagRepository) {

    fun getAllTags(): AllTagsResponse? {
        val tags = tagRepository.findAll()
        return AllTagsResponse(tags.map { it.text })
    }
}