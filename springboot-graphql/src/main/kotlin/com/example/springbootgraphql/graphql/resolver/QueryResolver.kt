package com.example.springbootgraphql.graphql.resolver

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.example.springbootgraphql.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class QueryResolver : GraphQLQueryResolver {
    fun version(): String {
        return "1.0"
    }

    fun users() = UserRepository.findAll()

    fun user(id: String) = UserRepository.find(id)
}