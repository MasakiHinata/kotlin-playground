package com.example.springbootgraphql.graphql.resolver

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.example.springbootgraphql.models.User
import com.example.springbootgraphql.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class MutationResolver : GraphQLMutationResolver {

    fun addUser(id: String, name: String, age: Int): User {
        val user = User(id, name, age)
        UserRepository.save(user)
        return user
    }

    fun deleteUser(id: String) : User? {
        val user = UserRepository.find(id)
        UserRepository.delete(id)
        return user
    }
}