package com.bravoborja.citiboxdemo.domain.usecase

import com.bravoborja.citiboxdemo.common.State
import com.bravoborja.citiboxdemo.data.local.model.PostEntity
import com.bravoborja.citiboxdemo.domain.repository.PostsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class GetPostUseCase @Inject constructor(private val repository: PostsRepository) {

    fun getPost(postId: Long): Flow<State<PostEntity>> {
        return repository.getPost(postId)
    }
}
