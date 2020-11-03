package com.example.springbootmysql.service

import com.example.springbootmysql.entity.UserEntity
import com.example.springbootmysql.entity.UserEntity.Companion.toUsers
import com.example.springbootmysql.model.User
import com.example.springbootmysql.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
        private val repository: UserRepository
) {
    fun createUser(user: User){
        val entity = UserEntity(user)
        repository.save(entity)
    }

    fun showUser(): List<User> {
        val entities = repository.findAll()
        return entities.toUsers()
    }

    fun deleteUser(name: String) {
        repository.deleteByName(name)
    }
}