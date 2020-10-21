# Retrofit
[Retrofit公式](https://square.github.io/retrofit/)
型安全なHTTPクライアント

## Install
```kotlin
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.okhttp3:okhttp:4.9.0")
```

## GSON
Retrofitで取得したデータをKotlin/Javaの型に変換する

## Get Started
1. Interfaceを作成
    ```kotlin
    interface GitHubService {
        @GET("users")
        suspend fun users(): List<User>
    }
    ```

1. クライアントを作成
    ```kotlin
    private val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.github.com/")
            .client(client)
            .build()

    private val service: GitHubService = retrofit.create(GitHubService::class.java)
    ```

1. リクエスト
    ```kotlin
    suspend fun users(): List<User>? = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = service.users()
            response
        } catch (e: IOException) {
            null
        }
    }
    ```

## Request
Retrofit2.6以降`suspend`が利用可能になったが、同期でリクエストを送信する方法もある。
```kotlin
// 戻り値は Call<**> とする
@GET("users")
fun users(): Call<List<User>>
```

```kotlin
// リクエスト
return try {
    val response = service.usersSync().execute()
    response.body()
} catch (e: IOException) {
    null
}
```
```kotlin
// コールバック
service.users().enqueue(object : Callback<List<User>> {
    override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
        if (response.isSuccessful)
            println("onResponse: ${response.body()}")
    }

    override fun onFailure(call: Call<List<User>>, t: Throwable) {
        println("onFailure: $t")
    }
})
```
## GET
```kotlin
// パラメータを指定
@GET("users/{user}/repos")
suspend fun repositories(@Path("user") user: String): List<Repository>

// クエリを指定
@GET("users")
suspend fun users(@Query("sort") sort: String): List<User>

@GET("users")
suspend fun users(@QueryMap options: Map<String, String>): List<User>
```
## POST
```kotlin
@POST("users/new")
suspend fun createUser(@Body user: User)
```
## HEADER
```kotlin
@GET("users")
@Headers(
    "Accept: application/json",
    "User-Agent: Retrofit-Sample-App"
)
suspend fun usersWithHeader(): Response<List<User>>

//  引数としてヘッダーを指定することもできる
@GET("users")
suspend fun usersWithHeader(@Header("User-Agent") agent: String): List<User>
```

### 共通してヘッダーを付ける
`Interceptor`を継承し、追加したいヘッダーを指定する
```kotlin
class MyInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("X-Platform", "Android")
            .addHeader("X-Auth-Token", "123456789")
            .build()
        return chain.proceed(request)
    }

}
```
```kotlin
// クラアントをRetrofitに渡す
private val client = OkHttpClient
        .Builder()
        .addInterceptor(MyInterceptor())
        .build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.github.com/")
        .client(client)
        .build()
```