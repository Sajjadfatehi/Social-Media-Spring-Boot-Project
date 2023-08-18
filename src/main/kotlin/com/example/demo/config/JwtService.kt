package com.example.demo.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import java.util.function.Function
import kotlin.collections.HashMap
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@Service
class JwtService {

    companion object {
        const val SECRET_KEY = "f93e4ecf3fde042dc727bd30e552fffc9b4c9181dfe62f006a8d91b4d7b9faf8"
    }

    fun extractUserName(token: String): String? {

        return extractClaim(token, Claims::getSubject)
    }

    fun <T> extractClaim(token: String, claimResolver: Function<Claims, T>): T {
        val claims = extractAllClaims(token)
        return claimResolver.apply(claims)
    }

    fun extractAllClaims(token: String): Claims {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .body
    }

    fun generateToken(userDetails: UserDetails): String {
        return generateToken(HashMap(), userDetails)
    }

    fun generateToken(
        extraClaims: Map<String, JvmType.Object>,
        userDetails: UserDetails
    ): String {
        return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            /** expire jwt in 10 days */
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 10))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    fun isTokenValid(
        token: String,
        userDetails: UserDetails
    ): Boolean {
        val username = extractUserName(token)
        return (username == userDetails.username && !isTokenExpired(token))
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token, Claims::getExpiration)
    }

    private fun getSignInKey(): Key {
        val keyBytes = Decoders.BASE64.decode(SECRET_KEY)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}