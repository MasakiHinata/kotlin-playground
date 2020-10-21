package org.example.src

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.example.src.model.User
import org.example.src.retrofit.GitHubApi
import org.example.src.retrofit.GitHubService

fun main(): Unit = runBlocking {
    launch {
        val user = User("abc", 100, "https://example.com")
        GitHubApi.createUser(user)
    }
}