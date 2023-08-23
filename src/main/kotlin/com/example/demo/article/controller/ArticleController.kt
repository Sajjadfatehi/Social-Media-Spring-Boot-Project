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
    fun getUserArticlesByUserName(
        @RequestHeader("Authorization") token: String,
        @RequestParam("author") username: String,
    ): ResponseEntity<ArticleListResponse> {
        return ResponseEntity.ok(service.getUserArticlesByUserName(username,token))
    }

    @GetMapping("/{slug}")
    fun getArticleBySlug(
        @RequestHeader("Authorization") token: String,
        @PathVariable("slug") slug: String
    ): ResponseEntity<ArticleWrapper<SingleArticleResponse>> {
        return ResponseEntity.ok(
            service.getArticleBySlug(slug,token)
        )
    }

    @GetMapping("/tag")
    fun getTagArticles(
        @RequestHeader("Authorization") token: String,
        @RequestParam("tag") tag: String
    ): ResponseEntity<ArticleListResponse> {
        return ResponseEntity.ok(
            service.getTagArticles(tag,token)
        )
    }

    @PutMapping("/{slug}")
    fun editArticle(
        @RequestHeader("Authorization") token: String,
        @RequestBody() body: EditArticleRequestWrapper,
        @PathVariable("slug") slug: String,
    ): ResponseEntity<ArticleWrapper<SingleArticleResponse>> {
        return ResponseEntity.ok(service.editArticle(body, slug,token))
    }

    @DeleteMapping("/{slug}")
    fun deleteArticle(@PathVariable("slug") slug: String): ResponseEntity<Unit> {
        return ResponseEntity.ok(service.deleteArticle(slug))
    }

    @PostMapping("/{slug}/favorite")
    fun bookmarkArticle(
        @RequestHeader("Authorization") token: String,
        @PathVariable("slug") slug: String,
    ): ResponseEntity<ArticleWrapper<SingleArticleResponse>> {
        return ResponseEntity.ok(service.bookmarkArticle(token, slug))
    }

    @DeleteMapping("/{slug}/favorite")
    fun removeFromBookmarks(
        @RequestHeader("Authorization") token: String,
        @PathVariable("slug") slug: String,
    ): ResponseEntity<ArticleWrapper<SingleArticleResponse>> {
        return ResponseEntity.ok(service.removeFromBookmarks(token, slug))
    }

    @GetMapping("/favorite")
    fun getBookmarkedArticles(
        @RequestHeader("Authorization") token: String,
        @RequestParam("favorited") username: String,
    ): ResponseEntity<ArticleListResponse> {
        return ResponseEntity.ok(service.getBookmarkedArticles(username,token))
    }
}