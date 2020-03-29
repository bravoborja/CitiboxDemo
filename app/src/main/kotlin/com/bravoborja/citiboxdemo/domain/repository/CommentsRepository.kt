package com.bravoborja.citiboxdemo.domain.repository

import com.bravoborja.citiboxdemo.common.State
import com.bravoborja.citiboxdemo.data.local.model.CommentEntity
import kotlinx.coroutines.flow.Flow

interface CommentsRepository {
    fun getComments(postId: Long): Flow<State<List<CommentEntity>>>
}