package com.accenture.innersource

data class UserDTO(val user: User)

data class User(
    val email: String? = null,
    val token: String? = null,
    val username: String? = null,
    val password: String? = null
)

val users = hashMapOf(
        0 to User("user@user.com", "", "user", "user"),
        1 to User("admin@admin.com", "", "admin", "admin")
)

data class Item(val name: String, val quantity: Int)

val items = hashMapOf(
        0 to Item("Pizza", 2),
        1 to Item("Soup", 10),
        2 to Item("Apple", 50)
)

data class Message(val message: String)

val messages = mutableListOf(
        Message("hello"),
        Message("ola")
)
