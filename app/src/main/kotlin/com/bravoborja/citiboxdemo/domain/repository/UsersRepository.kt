package com.bravoborja.citiboxdemo.domain.repository

import com.bravoborja.citiboxdemo.common.State
import com.bravoborja.citiboxdemo.data.local.model.UserEntity
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    fun getAuthor(userId: Long): Flow<State<UserEntity>>
}