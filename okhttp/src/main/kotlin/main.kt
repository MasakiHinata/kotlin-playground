import client.GetClient
import interceptor.Interceptors
import client.PostClient
import github.GitHubClient
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient

fun main(): Unit = runBlocking {
    val client = OkHttpClient()
    interceptor()
}

fun get(client: OkHttpClient) {
    GetClient.synchronousGet(client)
    GetClient.asynchronousGet(client)
}

fun post(client: OkHttpClient) {
    PostClient.postString(client)
}

fun github(client: OkHttpClient) {
    GitHubClient.getUsers(client)
}

fun interceptor() {
    Interceptors.applicationInterceptor()
    Interceptors.networkInterceptor()
}