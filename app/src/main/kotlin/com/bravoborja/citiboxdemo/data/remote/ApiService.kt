package com.bravoborja.citiboxdemo.data.remote

import com.bravoborja.citiboxdemo.data.remote.model.Comment
import com.bravoborja.citiboxdemo.data.remote.model.Post
import com.bravoborja.citiboxdemo.data.remote.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id: Long): Response<Post>

    @GET("users")
    suspend fun getUsers(): Response<List<User>>

    @GET("posts/{id}/comments")
    suspend fun getComments(@Path("id") id: Long): Response<List<Comment>>
}