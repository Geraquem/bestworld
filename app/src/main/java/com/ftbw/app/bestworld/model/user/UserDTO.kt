package com.ftbw.app.bestworld.model.user

data class UserDTO (
    val name: String = "",
    val email: String = "",
    val aboutYou: String = "",
    val key: String = "",
    val imageURL: String = "",
    val type: String = "",
    var addedCount: Long = 0,
)