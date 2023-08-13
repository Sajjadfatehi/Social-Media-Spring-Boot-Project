package com.example.demo.config

import com.example.demo.users.repoistory.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class ApplicationConfig(val userRepository: UserRepository) {


    @Bean
    fun userDetailsService(): UserDetailsService {
        return UserDetailsService {
            userRepository.findByEmail(it!!).orElseThrow {
                UsernameNotFoundException("user not found")
            }
        }
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authProvide = DaoAuthenticationProvider()
        authProvide.setUserDetailsService(userDetailsService())
        authProvide.setPasswordEncoder(passwordEncoder())
        return authProvide
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}