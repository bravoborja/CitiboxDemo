package com.bravoborja.citiboxdemo.domain.repository

import com.bravoborja.citiboxdemo.common.State
import com.bravoborja.citiboxdemo.domain.model.CommentModel
import kotlinx.coroutines.flow.Flow

interface CommentsRepository {
    fun getComments(postId: Long): Flow<State<List<CommentModel>>>
}