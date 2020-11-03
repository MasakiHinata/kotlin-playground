package com.example.springbootgetstarted.controller

import com.example.springbootgetstarted.model.User
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class UserController {

    private val users: MutableList<User> = mutableListOf()

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
            @RequestBody(required = true)
            user: User
    ){
        users += user
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    fun list(): List<User>{
        return users
    }
}