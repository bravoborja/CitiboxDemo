package com.bravoborja.citiboxdemo.data.repository

import com.bravoborja.citiboxdemo.common.State
import com.bravoborja.citiboxdemo.data.local.dao.UsersDao
import com.bravoborja.citiboxdemo.data.local.model.UserEntity
import com.bravoborja.citiboxdemo.data.remote.ApiService
import com.bravoborja.citiboxdemo.data.remote.model.User
import com.bravoborja.citiboxdemo.domain.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class UsersDataRepository @Inject constructor(
    private val usersDao: UsersDao,
    private val apiService: ApiService
) : UsersRepository {

    override fun getAuthor(userId: Long): Flow<State<UserEntity>> {
        return object : NetworkBoundRepository<UserEntity, List<User>>() {

            override suspend fun saveRemoteData(data: List<User>) =
                usersDao.insertUsers(data.map {
                    UserEntity(
                        it.id,
                        it.name,
                        it.username,
                        it.email
                    )
                })

            override fun fetchFromLocal(): Flow<UserEntity> = usersDao.getAuthor(userId)

            override suspend fun fetchFromRemote(): Response<List<User>> = apiService.getUsers()

        }.asFlow().flowOn(Dispatchers.IO)
    }
}