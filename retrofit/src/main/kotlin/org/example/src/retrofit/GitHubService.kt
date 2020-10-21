package org.example.src.retrofit

import org.example.src.model.Repository
import org.example.src.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface GitHubService {
    @GET("users")
    fun usersSync(): Call<List<User>>

    @GET("users")
    suspend fun users(): List<User>

    @GET("users/{user}/repos")
    suspend fun repositories(@Path("user") user: String): List<Repository>

    @GET("users")
    suspend fun users(@Query("sort") sort: String): List<User>

    @GET("users")
    suspend fun users(@QueryMap options: Map<String, String>): List<User>

    @GET("users")
    @Headers(
        "Accept: application/json",
        "User-Agent: Retrofit-Sample-App"
    )
    suspend fun usersWithHeader(): Response<List<User>>

    @GET("users")
    suspend fun usersWithHeader(@Header("User-Agent") agent: String): List<User>

    @POST("users/new")
    suspend fun createUser(@Body user: User)

}