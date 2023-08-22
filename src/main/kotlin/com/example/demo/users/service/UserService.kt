package com.example.demo.users.service

import com.example.demo.config.JwtService
import com.example.demo.share.Constants
import com.example.demo.users.entity.UserEntity
import com.example.demo.users.mapper.toUserDTO
import com.example.demo.users.model.UserDtoWrapper
import com.example.demo.users.repoistory.FollowRepository
import com.example.demo.users.repoistory.UserRepository
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
@Transactional
class UserService(
    val userRepository: UserRepository,
    val jwtService: JwtService,
    val followRepository: FollowRepository
) {

    fun findUserByEmail(token: String, email: String): UserDtoWrapper {
        var userEntity = userRepository.findByUsername(email).getOrNull()
        if (userEntity == null) {
            userEntity = userRepository.findByEmail(email).orElseThrow {
                EntityNotFoundException("there is no user with username: $email")
            }
        }

        val user = findUserByToken(token)
        val isFollowing = user.isFollowing(userEntity!!)
        return UserDtoWrapper(userEntity.toUserDTO(isFollowing))
    }

    fun followUser(token: String, userName: String): UserDtoWrapper? {
        val follower = findUserByToken(token)
        val following =
            userRepository.findByEmail(userName).orElseThrow { EntityNotFoundException("Following user not found") }

        follower.follow(following)
        userRepository.save(follower)

        val userDTO = following.toUserDTO(true)
        return UserDtoWrapper(userDTO)
    }

    fun unFollowUser(token: String, userName: String): UserDtoWrapper? {
        val follower = findUserByToken(token)
        val following =
            userRepository.findByEmail(userName).orElseThrow { EntityNotFoundException("Following user not found") }

        follower.unfollow(following)
        userRepository.save(follower)
        userRepository.save(following)

        followRepository.deleteByBothId(followerId = follower.id, followingId = following.id)

        val userDTO = following.toUserDTO(false)
        return UserDtoWrapper(userDTO)
    }

    private fun findUserByToken(token: String): UserEntity {
        val email = jwtService.extractUserName(token.substring(Constants.JWT_START_INDEX))
        return userRepository.findByEmail(email.orEmpty()).get()
    }

}