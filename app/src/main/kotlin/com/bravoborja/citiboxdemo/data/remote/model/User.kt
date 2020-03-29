package com.bravoborja.citiboxdemo.data.remote.model

data class User(
    val id: Long,
    val postId: Long,
    val name: String,
    val username: String,
    val email: String
)