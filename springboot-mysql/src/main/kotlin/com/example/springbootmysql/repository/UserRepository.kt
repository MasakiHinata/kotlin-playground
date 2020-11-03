package com.example.springbootmysql.repository

import com.example.springbootmysql.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface UserRepository: JpaRepository<UserEntity, Int> {
//    @Modifying(clearAutomatically = true)
//    @Transactional
//    @Query("DELETE FROM users WHERE user_name=:name", nativeQuery = true)
//    fun deleteByName(@Param("name") name: String)

    @Transactional
    fun deleteByName(name: String)
}