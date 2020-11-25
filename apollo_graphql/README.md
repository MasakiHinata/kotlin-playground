# Apollo GraphQL
[Introduction](https://www.apollographql.com/docs/android/tutorial/00-introduction)

[Kotlin tutorial](https://www.apollographql.com/docs/android/essentials/get-started-kotlin/)

## 準備

```kotlin
plugins {
    id("com.apollographql.apollo").version("2.2.0")
}

dependencies {
    // apollo
    implementation ("com.apollographql.apollo:apollo-runtime:2.2.0")
    implementation ("com.apollographql.apollo:apollo-coroutines-support:2.2.0")
}

apollo {
    // Kotlinコードを生成するプラグイン
    generateKotlinModels.set(true)
}
```

### スキーマをダウンロード

```bash
./gradlew downloadApolloSchema \
	# スキーマのある場所
  --endpoint="https://your.domain/graphql/endpoint" \
	# スキーマをダウンロードする場所
  --schema="app/src/main/graphql/com/example" \
	# 認証が必要な場合
  --header="Authorization: Bearer $TOKEN"
```

### クライアントの作成

```kotlin
val apolloClient = ApolloClient.builder()
        .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com")
        .build()
```

### クエリを追加

```graphql
# graphql > LaunchList.graphql
query LaunchList {
    launches {
        cursor
        hasMore
        launches {
            id
            site
        }
    }
}
```

### クエリを実行

```kotlin
val responseList = apolloClient.query(LaunchListQuery()).toDeferred().await()
println(responseList.data?.launches)
```

## クエリ
### シンプルなクエリ
graphql > LaunchList.graphql
```graphql
query LaunchList {
    launches {
        cursor
        launches {
            id
            site
        }
    }
}
```

```kotlin
val response = apolloClient.query(LaunchListQuery()).toDeferred().await()
println(response.data?.launches)
```

### パラメータ付きのクエリ

graphql > LaunchDetails.graphql

```kotlin
query LaunchDetails($id:ID!) {
    launch(id: $id) {
        id
        site
    }
}
```

```kotlin
val response = apolloClient.query(LaunchDetailsQuery(id = launch.id)).toDeferred().await()
println(response.data?.launch)
```

## Mutation
```graphql
mutation Login($email: String) {
    login(email: $email)
}
```

```kotlin
val response = apolloClient.mutate(LoginMutation(email = Input.fromNullable("example@example.com"))).toDeferred().await()
println(response.data?.login)
```
## Subscription
サーバー側で変更があった時に、情報を受け取ることができる
### スキーマ

```graphql
subscription TripsBooked {
    tripsBooked
}
```

### クライアントを作成

```kotlin
val okHttpClient = OkHttpClient.Builder().build()

// websocketを指定
val apolloClient = ApolloClient.builder()
        .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/")
        .subscriptionTransportFactory(
            WebSocketSubscriptionTransport.Factory(
                "wss://apollo-fullstack-tutorial.herokuapp.com/graphql",
                okHttpClient
            )
        )
        .build()
```

### 変更をリッスンする

```kotlin
apolloClientWithAuth.subscribe(TripsBookedSubscription()).toFlow().collect {
    val trips = it.data?.tripsBooked
    println("stream: $trips")
}
```

## 認証
### Interceptorを作成

```kotlin
class AuthorizationInterceptor(
    private val token: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", token)
            .build()
        return chain.proceed(request)
    }
}
```

### クライアントに登録

```kotlin
val interceptor = AuthorizationInterceptor(responseLogin.data?.login ?: "")
val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(interceptor)
    .build()

val apolloClientWithAuth = ApolloClient.builder()
        .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/")
        .okHttpClient(okHttpClient)
        .build()
```