package com.bravoborja.citiboxdemo.domain.usecase

import com.bravoborja.citiboxdemo.common.State
import com.bravoborja.citiboxdemo.data.local.model.UserEntity
import com.bravoborja.citiboxdemo.domain.repository.UsersRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class GetAuthorUseCase @Inject constructor(private val repository: UsersRepository) {

    fun getAuthor(userId: Long): Flow<State<UserEntity>> {
        return repository.getAuthor(userId)
    }
}
