package com.example.demo.article.controller

import com.example.demo.article.model.*
import com.example.demo.article.service.ArticleService
import com.example.demo.share.Constants
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("${Constants.BASE_URL}/articles")
class ArticleController(val service: ArticleService) {

    @PostMapping()
    fun createArticle(
        @RequestHeader("Authorization") token: String,
        @RequestBody request: ArticleWrapper<CreateArticleRequest>,
    ): ResponseEntity<ArticleWrapper<SingleArticleResponse>> {
        return ResponseEntity.ok(service.saveNewArticle(request.article, token))
    }

    @GetMapping
    fun getUserArticlesByUserName(@RequestParam("author") username: String): ResponseEntity<ArticleListResponse> {
        return ResponseEntity.ok(service.getUserArticlesByUserName(username))
    }

    @GetMapping("/{slug}")
    fun getArticleBySlug(@PathVariable("slug") slug: String): ResponseEntity<ArticleWrapper<SingleArticleResponse>> {
        return ResponseEntity.ok(
            service.getArticleBySlug(slug)
        )
    }

    @GetMapping("/tag")
    fun getTagArticles(@RequestParam("tag") tag: String): ResponseEntity<ArticleListResponse> {
        return ResponseEntity.ok(
            service.getTagArticles(tag)
        )
    }

    @PutMapping("/{slug}")
    fun editArticle(
        @RequestBody() body: EditArticleRequestWrapper,
        @PathVariable("slug") slug: String,
    ): ResponseEntity<ArticleWrapper<SingleArticleResponse>> {
        return ResponseEntity.ok(service.editArticle(body, slug))
    }

    @DeleteMapping("/{slug}")
    fun deleteArticle(@PathVariable("slug") slug: String): ResponseEntity<Unit> {
        return ResponseEntity.ok(service.deleteArticle(slug))
    }
}