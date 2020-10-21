package org.example.src.retrofit

import org.example.src.model.Repository
import org.example.src.model.User
import retrofit2.Call
import retrofit2.http.*

interface GitHubService {
    @GET("users")
    fun usersSync(): Call<List<User>>

    @GET("users")
    suspend fun user(): List<User>

    @GET("users/{user}/repos")
    suspend fun repositories(@Path("user") user: String): List<Repository>

    @GET("users")
    suspend fun users(@Query("sort") sort: String): List<User>

    @GET("users")
    suspend fun users(@QueryMap options: Map<String, String>): List<User>

    @POST("users/new")
    suspend fun createUser(@Body user: User)

}