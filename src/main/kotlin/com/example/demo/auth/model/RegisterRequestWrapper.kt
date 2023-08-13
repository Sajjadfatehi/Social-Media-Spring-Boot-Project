package com.example.demo.auth.model

import com.fasterxml.jackson.annotation.JsonProperty

data class RegisterRequestWrapper (
    @JsonProperty("user")
    val registerRequest: RegisterRequest
)