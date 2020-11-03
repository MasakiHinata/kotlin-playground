package com.example.springbootmysql.entity

import com.example.springbootmysql.model.User
import javax.persistence.*

@Entity
@Table(name="users", schema = "user_schema")
data class UserEntity (
    @Id
    @Column("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column("user_name")
    val name: String,

    @Column("age")
    val age: Int
){
    constructor(user: User): this(0, user.name, user.age)

    fun toUser(): User{
        return User(
                name = this.name,
                age = this.age
        )
    }

    companion object{
        fun List<UserEntity>.toUsers(): List<User>{
            return this.map { it.toUser() }
        }
    }
}