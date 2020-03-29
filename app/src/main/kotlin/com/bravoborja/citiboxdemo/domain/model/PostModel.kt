package com.bravoborja.citiboxdemo.domain.model

data class PostModel(
    val id: Long? = 0,
    val userId: Long? = 0,
    val title: String? = null,
    val body: String? = null,
    val imageUrl: String? = null
)