package com.example.springbootgrpc.controller

import greet.Greet
import greet.GreetServiceGrpc
import io.grpc.ManagedChannelBuilder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetController {

    @GetMapping("/unary")
    fun greet(): String {
        val channel = ManagedChannelBuilder
                .forAddress("localhost", 6565)
                .usePlaintext(true)
                .build()

        val stub = GreetServiceGrpc.newBlockingStub(channel)

        val greeting = Greet.Greeting.newBuilder()
                .setFirstName("Taro")
                .setLastName("Tanaka")

        val request = Greet.GreetRequest.newBuilder()
                .setGreeting(greeting)
                .build()

        val response = stub.greet(request)

        return response.result
    }
}