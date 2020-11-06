package com.example.springbootgrpc.service

import greet.Greet
import greet.GreetServiceGrpc
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class GreetService: GreetServiceGrpc.GreetServiceImplBase() {
    override fun greet(request: Greet.GreetRequest, responseObserver: StreamObserver<Greet.GreetResponse>) {
        val firstName = request.greeting.firstName
        val lastName = request.greeting.lastName
        val response = Greet.GreetResponse
                .newBuilder()
                .setResult("Hello $firstName $lastName")
                .build()
        
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}