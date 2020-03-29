package com.bravoborja.citiboxdemo.domain.repository

import com.bravoborja.citiboxdemo.common.State
import com.bravoborja.citiboxdemo.domain.model.PostModel
import kotlinx.coroutines.flow.Flow

interface PostsRepository {
    fun getPosts(): Flow<State<List<PostModel>>>

    fun getPost(postId: Long): Flow<State<PostModel>>
}