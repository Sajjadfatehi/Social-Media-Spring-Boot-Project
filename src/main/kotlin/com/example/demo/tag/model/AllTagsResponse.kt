package com.example.demo.tag.model

import com.fasterxml.jackson.annotation.JsonProperty

data class AllTagsResponse(
    @JsonProperty("tags")
    val tags: List<String>,
)