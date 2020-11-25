import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.coroutines.toFlow
import com.apollographql.apollo.subscription.WebSocketSubscriptionTransport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient

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
    val responseLogin =
        apolloClient.mutate(LoginMutation(email = Input.fromNullable("example@example.com"))).toDeferred().await()
    println(responseLogin.data?.login)

    // Authentication
    val interceptor = AuthorizationInterceptor(responseLogin.data?.login ?: "")
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    val apolloClientWithAuth = ApolloClient.builder()
        .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/")
        .okHttpClient(okHttpClient)
        .subscriptionTransportFactory(
            WebSocketSubscriptionTransport.Factory(
                "wss://apollo-fullstack-tutorial.herokuapp.com/graphql",
                okHttpClient
            )
        )
        .build()

    // Subscriptions
    launch(Dispatchers.IO) {
        apolloClientWithAuth.subscribe(TripsBookedSubscription()).toFlow().collect {
            val trips = it.data?.tripsBooked
            println("stream: $trips")
        }
    }

    delay(1000)
    val responseReservation = apolloClientWithAuth.mutate(BookTripMutation(id = "83")).toDeferred().await()
    println(responseReservation.data?.bookTrips?.message)


}