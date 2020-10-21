package org.example.src.retrofit

import org.example.src.model.Repository
import org.example.src.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {
    @GET("users")
    fun usersSync(): Call<List<User>>

    @GET("users")
    suspend fun user(): List<User>

    @GET("users/{user}/repos")
    suspend fun repositories(@Path("user") user: String): List<Repository>
}