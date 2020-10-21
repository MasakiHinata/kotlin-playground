package org.example.src

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.example.src.retrofit.GitHubApi

fun main(): Unit = runBlocking {
    launch {
        println(GitHubApi.usersWithHeader())
    }
}