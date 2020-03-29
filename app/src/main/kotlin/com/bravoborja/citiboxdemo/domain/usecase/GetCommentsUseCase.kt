package com.bravoborja.citiboxdemo.domain.usecase

import com.bravoborja.citiboxdemo.common.State
import com.bravoborja.citiboxdemo.domain.model.CommentModel
import com.bravoborja.citiboxdemo.domain.repository.CommentsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class GetCommentsUseCase @Inject constructor(private val repository: CommentsRepository) {

    fun getComments(postId: Long): Flow<State<List<CommentModel>>> {
        return repository.getComments(postId)
    }
}
