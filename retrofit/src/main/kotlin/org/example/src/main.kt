package org.example.src

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.example.src.retrofit.GitHubApi

fun main(): Unit = runBlocking {
    this.launch {
        val user = GitHubApi.users()
        if(user != null){
            val repository = GitHubApi.repositories(user[0])
            println(repository)
        }
    }
}