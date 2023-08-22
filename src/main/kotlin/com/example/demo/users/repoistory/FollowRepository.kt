package com.example.demo.users.repoistory

import com.example.demo.users.entity.FollowEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface FollowRepository:JpaRepository<FollowEntity,Long> {

    @Modifying
    @Query("DELETE FROM follow WHERE follower.id= :followerId AND following.id= :followingId")
    fun deleteByBothId(@Param("followerId") followerId:Long, @Param("followingId")followingId:Long)
}