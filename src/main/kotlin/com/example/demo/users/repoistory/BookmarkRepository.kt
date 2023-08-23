package com.example.demo.users.repoistory

import com.example.demo.users.entity.BookmarkEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookmarkRepository :JpaRepository<BookmarkEntity,Long>{

}
