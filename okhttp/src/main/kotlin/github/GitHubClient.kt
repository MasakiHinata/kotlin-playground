package github

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

object GitHubClient {
    fun getUsers(client: OkHttpClient): List<GitHubUser> {
        val request = Request.Builder()
            .url("https://api.github.com/users")
            .build()

        val gson = Gson()
        // JSONがリスト形式になっているときは Array<> とする
        val userAdapter = gson.getAdapter(Array<GitHubUser>::class.java)

        client
            .newCall(request)
            .execute()
            .use { response ->
                if (!response.isSuccessful)
                    throw IOException("Unexpected code $response")
                val users = userAdapter.fromJson(response.body!!.string())
                users.map { println(it) }
                return users.toList()
            }
    }

    fun getUser(client: OkHttpClient, user: GitHubUser): GitHubUser {
        val request = Request.Builder()
            .url("https://api.github.com/users/${user.login}")
            .build()

        val gson = Gson()
        val userAdapter = gson.getAdapter(GitHubUser::class.java)

        client
            .newCall(request)
            .execute()
            .use { response ->
                if (!response.isSuccessful)
                    throw IOException("Unexpected code $response")
                val user = userAdapter.fromJson(response.body!!.string())
                println(user)
                return user
            }
    }
}