package com.bravoborja.citiboxdemo.domain.usecase

import com.bravoborja.citiboxdemo.common.State
import com.bravoborja.citiboxdemo.domain.model.PostModel
import com.bravoborja.citiboxdemo.domain.repository.PostsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class GetPostUseCase @Inject constructor(private val repository: PostsRepository) {

    fun getPost(postId: Long): Flow<State<PostModel>> {
        return repository.getPost(postId)
    }
}
