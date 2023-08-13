package com.example.demo.auth.model

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginRequestWrapper(
    @JsonProperty("user")
    val loginRequest:LoginRequest
)
