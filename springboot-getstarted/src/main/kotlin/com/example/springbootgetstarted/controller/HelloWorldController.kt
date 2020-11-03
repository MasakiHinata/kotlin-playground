package com.example.springbootgetstarted.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldController {
    @GetMapping("/")
    fun helloWorld(): String{
        return "Hello World"
    }

    @GetMapping("/greet")
    fun greet(
            @RequestParam(value = "name")
            name: String
    ): String{
        return "Hello $name"
    }
}