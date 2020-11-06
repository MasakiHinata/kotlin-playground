package client

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

object PostClient {
    fun postString(client: OkHttpClient) {
        // GSONを用いてJSONを作成
        val user = User("Alice", 18)
        val gson = Gson()
        val jsonStr = gson.toJson(user)

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = jsonStr.toRequestBody(mediaType)
        val request = Request.Builder()
            .url("https://httpbin.org/post")
            .post(body)
            .build()

        client
            .newCall(request)
            .execute()
            .use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                println(response.body!!.string())
            }
    }
}