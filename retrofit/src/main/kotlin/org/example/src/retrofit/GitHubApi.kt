package org.example.src.retrofit

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import org.example.src.model.Repository
import org.example.src.model.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object GitHubApi {
    private val client = OkHttpClient
        .Builder()
        .addInterceptor(MyInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.github.com/")
        .client(client)
        .build()

    private val service: GitHubService = retrofit.create(GitHubService::class.java)

    // GET
    suspend fun users(): List<User>? = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = service.users()
            response
        } catch (e: IOException) {
            null
        }
//        return try {
//            val response = service.usersSync().execute()
//            response.body()
//        } catch (e: IOException) {
//            null
//        }

//        service.usersSync().enqueue(object : Callback<List<User>> {
//            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
//                if (response.isSuccessful)
//                    println("onResponse: ${response.body()}")
//            }
//
//            override fun onFailure(call: Call<List<User>>, t: Throwable) {
//                println("onFailure: $t")
//            }
//        })
    }

    // パラメータ付きのGET
    suspend fun repositories(user: User): List<Repository>? {
        return try {
            service.repositories(user.login)
        } catch (e: IOException) {
            println(e)
            null
        }
    }

    // クエリ付きのGET
    suspend fun users(desc: Boolean): List<User>? {
        return try {
            service.users(if (desc) "desc" else "asc")
        } catch (e: IOException) {
            println(e)
            null
        }
    }

    // HEADER付きのGET
    suspend fun usersWithHeader(): List<User>? {
        return try {
            val response = service.usersWithHeader()
            println(response.body())
            println(response.headers())
            println(response.code())
            response.body()
        } catch (e: IOException) {
            println(e)
            null
        }
    }

    // POST
    suspend fun createUser(user: User) {
        service.createUser(user)
    }
}