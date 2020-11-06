package interceptor

import client.GetClient
import okhttp3.OkHttpClient
import okhttp3.Request

object Interceptors {
    val redirectionRequest = Request.Builder()
        .url("http://www.publicobject.com/helloworld.txt")
        .build()

    fun applicationInterceptor() {
        val client = OkHttpClient.Builder()
            .addInterceptor (JsonHeaderInterceptor())
            .addInterceptor(LoggingInterceptor())
            .build()

        GetClient.synchronousGet(client, redirectionRequest)
    }

    fun networkInterceptor() {
        // リダイレクトにも対応する
        val client = OkHttpClient.Builder()
            .addNetworkInterceptor (JsonHeaderInterceptor())
            .addNetworkInterceptor(LoggingInterceptor())
            .build()

        GetClient.synchronousGet(client, redirectionRequest)
    }
}


