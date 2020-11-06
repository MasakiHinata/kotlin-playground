# OkHttp
## GET
### リクエストを作成

```kotlin
val client = OkHttpClient()

val request = Request.Builder()
    .url("https://httpbin.org/get")
    .build()
```

### 同期

`.execute()`によりリクエストを実行

```kotlin
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
```

### 非同期

`enqueue()`によってリクエストを実行し、コールバック関数を利用

```kotlin
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
```
## POST
### JSONを作成する

GSONを利用

```groovy
implementation("com.google.code.gson:gson:2.8.6")
```

```kotlin
// GSONを用いてJSONを作成
val user = User("Alice", 18)
val gson = Gson()
val jsonStr = gson.toJson(user)
```

### リクエストを作成

```kotlin
val mediaType = "application/json; charset=utf-8".toMediaType()
val body = jsonStr.toRequestBody(mediaType)
val request = Request.Builder()
    .url("https://httpbin.org/post")
    .post(body)
    .build()
```

### 同期

```kotlin

client
    .newCall(request)
    .execute()
    .use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
        println(response.body!!.string())
    }
```

### 非同期

非同期の場合はenqueue()により実行する

## Interceptor
OkHttpの処理に割り込むことができる

<img src="https://square.github.io/okhttp/images/interceptors%402x.png" height="300px">

JSONのヘッダーを付けるInterceptor

```kotlin
class JsonHeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("content-type","application/json")
            .build()

        return chain.proceed(newRequest)
    }
}
```

リクエストのログを取るInterceptor

```kotlin
class LoggingInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        println("=======  Logging  =========")
        println(chain.request().body)
        return chain.proceed(chain.request())
    }
}
```

### Application Interceptor

- RedirectやRetryといった中間応答には反応できない
- Cacheレスポンスにも反応する
- アプリケーションが発行するオリジナルリクエストが監視対象

```kotlin
val client = OkHttpClient.Builder()
            .addInterceptor (JsonHeaderInterceptor())
            .addInterceptor(LoggingInterceptor())
            .build()
```

### Network Interceptor

- RedirectやRetryといった中間応答にも反応できる
- Cacheレスポンスには反応できない
- ネットワークへ発行されるリクエストが監視対象

```kotlin
val client = OkHttpClient.Builder()
            .addNetworkInterceptor (JsonHeaderInterceptor())
            .addNetworkInterceptor(LoggingInterceptor())
            .build()
```