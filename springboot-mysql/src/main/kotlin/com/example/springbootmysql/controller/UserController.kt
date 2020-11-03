package com.example.springbootmysql.controller

import com.example.springbootmysql.entity.UserEntity
import com.example.springbootmysql.entity.UserEntity.Companion.toUsers
import com.example.springbootmysql.model.User
import com.example.springbootmysql.repository.UserRepository
import com.example.springbootmysql.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
        private val service: UserService
) {

    @PostMapping("")
    fun create(@RequestBody user: User) {
        service.createUser(user)
    }

    @GetMapping("")
    fun show(): List<User>{
        return service.showUser()
    }

    @DeleteMapping("")
    fun delete(
            @RequestParam(value = "name", required = true)
            name: String
    ) {
        service.deleteUser(name)
    }
}