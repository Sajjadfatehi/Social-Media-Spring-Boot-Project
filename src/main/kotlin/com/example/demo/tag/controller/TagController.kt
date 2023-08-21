package com.example.demo.tag.controller

import com.example.demo.share.Constants
import com.example.demo.tag.model.AllTagsResponse
import com.example.demo.tag.service.TagService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("${Constants.BASE_URL}/tags")
class TagController(val service: TagService) {

    @GetMapping
    fun getAllTags():ResponseEntity<AllTagsResponse> {
        return ResponseEntity.ok(service.getAllTags())
    }

}