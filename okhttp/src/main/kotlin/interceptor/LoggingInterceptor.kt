package interceptor

import okhttp3.Interceptor
import okhttp3.Response

class LoggingInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        println("=======  Logging  =========")
        println(chain.request().body)
        return chain.proceed(chain.request())
    }

}
