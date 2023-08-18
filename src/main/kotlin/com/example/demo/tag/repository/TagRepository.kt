package com.example.demo.tag.repository

import com.example.demo.tag.entity.TagEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface TagRepository : JpaRepository<TagEntity, String> {
    fun findByText(tag: String): Optional<TagEntity>
}
