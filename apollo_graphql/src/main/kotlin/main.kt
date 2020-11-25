import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toDeferred
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val apolloClient = ApolloClient.builder()
        .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com")
        .build()

    // シンプルなQuery
    val responseList = apolloClient.query(LaunchListQuery()).toDeferred().await()
    println(responseList.data?.launches)

    // パラメータを使ったQuery(idを指定する)
    val launch = responseList.data?.launches?.launches?.first() ?: return@runBlocking
    val responseDetail = apolloClient.query(LaunchDetailsQuery(id = launch.id)).toDeferred().await()
    println(responseDetail.data?.launch)

    // Mutation
    val responseLogin = apolloClient.mutate(LoginMutation(email = Input.fromNullable("example@example.com"))).toDeferred().await()
    println(responseLogin.data?.login)
}