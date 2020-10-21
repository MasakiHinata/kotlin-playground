package org.example.src

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.example.src.model.User
import org.example.src.retrofit.GitHubApi
import org.example.src.retrofit.GitHubService

fun main(): Unit = runBlocking {
    launch {
        println(GitHubApi.usersWithHeader())
    }
}