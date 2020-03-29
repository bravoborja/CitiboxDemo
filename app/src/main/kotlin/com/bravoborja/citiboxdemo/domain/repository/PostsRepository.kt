package com.bravoborja.citiboxdemo.domain.repository

import com.bravoborja.citiboxdemo.common.State
import com.bravoborja.citiboxdemo.data.local.model.PostEntity
import kotlinx.coroutines.flow.Flow

interface PostsRepository {
    fun getPosts(): Flow<State<List<PostEntity>>>

    fun getPost(postId: Long): Flow<State<PostEntity>>
}