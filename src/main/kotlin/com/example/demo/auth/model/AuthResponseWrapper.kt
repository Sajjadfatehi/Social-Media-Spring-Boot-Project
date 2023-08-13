package com.example.demo.auth.model

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthResponseWrapper(
    @JsonProperty("user")
    val authResponse: AuthResponse
)