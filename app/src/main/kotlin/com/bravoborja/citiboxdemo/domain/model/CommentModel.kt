package com.bravoborja.citiboxdemo.domain.model

data class CommentModel(
    val id: Long? = 0,
    val postId: Long? = 0,
    val name: String? = null,
    val email: String? = null,
    val body: String? = null
)