package client

import okhttp3.*
import java.io.IOException

object GetClient {
    fun synchronousGet(client: OkHttpClient) {
        val request = Request.Builder()
            .url("https://httpbin.org/get")
            .build()

        client
            .newCall(request)
            .execute()
            .use { response ->
                if (!response.isSuccessful)
                    throw IOException("Unexpected code $response")

                for ((name, value) in response.headers) {
                    println("$name: $value")
                }
                println(response.body!!.string())
            }
    }

    fun synchronousGet(client: OkHttpClient, request: Request) {
        client
            .newCall(request)
            .execute()
            .use { response ->
                if (!response.isSuccessful)
                    throw IOException("Unexpected code $response")

                for ((name, value) in response.headers) {
                    println("$name: $value")
                }
                println(response.body!!.string())
            }
    }

    fun asynchronousGet(client: OkHttpClient) {
        val request = Request.Builder()
            .url("https://httpbin.org/get")
            .build()

        client
            .newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: java.io.IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) = response.use {
                    if (!response.isSuccessful)
                        throw IOException("Unexpected code $response")

                    for ((name, value) in response.headers) {
                        println("$name: $value")
                    }

                    println(response.body!!.string())
                    // 二回以上読み込もうとするとエラーになる(closeされているため)
                    // println(response.body!!.string())
                }

            })
    }
}