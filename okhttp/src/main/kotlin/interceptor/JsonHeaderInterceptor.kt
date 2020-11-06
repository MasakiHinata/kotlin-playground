package interceptor

import okhttp3.Interceptor
import okhttp3.Response

class JsonHeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // もとのリクエストにコンテントタイプヘッダーを付ける
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("content-type","application/json")
            .build()

        return chain.proceed(newRequest)
    }
}