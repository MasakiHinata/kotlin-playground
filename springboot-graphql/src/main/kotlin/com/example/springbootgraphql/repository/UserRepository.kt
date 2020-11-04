package com.example.springbootgraphql.repository

import com.example.springbootgraphql.models.User


object UserRepository {
    private val users: MutableList<User> = mutableListOf(
            User("alice-apple", "Alice", 19),
            User("bob-banana", "Bob", 20)
    )

    fun findAll(): List<User> = users

    fun find(id: String): User? = users.find { it.id == id }

    fun save(user: User) = users.add(user)

    fun delete(id: String) = users.removeIf { it.id ==  id}
}