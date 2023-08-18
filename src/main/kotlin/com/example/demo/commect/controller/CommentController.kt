package com.example.demo.commect.controller

import com.example.demo.commect.model.ArticleCommentsWrapper
import com.example.demo.commect.model.CommentRequestWrapper
import com.example.demo.commect.service.CommentService
import com.example.demo.share.Constants
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("${Constants.BASE_URL}/articles/")
class CommentController(val service: CommentService) {

    @PostMapping("{slug}/comments")
    fun sendComment(
        @RequestHeader("Authorization") token: String,
        @PathVariable("slug") slug: String,
        @RequestBody commentRequestWrapper: CommentRequestWrapper,
    ): ResponseEntity<Unit> {
        return ResponseEntity.ok(
            service.saveComment(
                token = token,
                slug = slug,
                commentRequestWrapper = commentRequestWrapper
            )
        )
    }

    @GetMapping("{slug}/comments")
    fun getArticleComments(@PathVariable("slug") slug: String): ResponseEntity<ArticleCommentsWrapper> {
        return ResponseEntity.ok(
            service.getArticleComments(slug)
        )
    }
}