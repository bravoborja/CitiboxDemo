package com.bravoborja.citiboxdemo.data.repository

import com.bravoborja.citiboxdemo.common.State
import com.bravoborja.citiboxdemo.data.local.dao.CommentsDao
import com.bravoborja.citiboxdemo.data.local.model.CommentEntity
import com.bravoborja.citiboxdemo.data.remote.ApiService
import com.bravoborja.citiboxdemo.data.remote.model.Comment
import com.bravoborja.citiboxdemo.domain.repository.CommentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class CommentsDataRepository @Inject constructor(
    private val commentsDao: CommentsDao,
    private val apiService: ApiService
) :
    CommentsRepository {
    override fun getComments(postId: Long): Flow<State<List<CommentEntity>>> {
        return object : NetworkBoundRepository<List<CommentEntity>, List<Comment>>() {

            override suspend fun saveRemoteData(data: List<Comment>) =
                commentsDao.insertComments(data.map {
                    CommentEntity(
                        it.id,
                        it.postId,
                        it.name,
                        it.email,
                        it.body
                    )
                })

            override fun fetchFromLocal(): Flow<List<CommentEntity>> =
                commentsDao.getComments(postId)

            override suspend fun fetchFromRemote(): Response<List<Comment>> =
                apiService.getComments(postId)

        }.asFlow().flowOn(Dispatchers.IO)
    }
}