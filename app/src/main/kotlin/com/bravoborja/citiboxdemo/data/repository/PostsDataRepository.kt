package com.bravoborja.citiboxdemo.data.repository

import com.bravoborja.citiboxdemo.common.State
import com.bravoborja.citiboxdemo.data.local.dao.PostsDao
import com.bravoborja.citiboxdemo.data.local.model.PostEntity
import com.bravoborja.citiboxdemo.data.remote.ApiService
import com.bravoborja.citiboxdemo.data.remote.model.Post
import com.bravoborja.citiboxdemo.domain.repository.PostsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class PostsDataRepository @Inject constructor(
    private val postsDao: PostsDao,
    private val apiService: ApiService
) :
    PostsRepository {
    override fun getPosts(): Flow<State<List<PostEntity>>> {
        return object : NetworkBoundRepository<List<PostEntity>, List<Post>>() {

            override suspend fun saveRemoteData(data: List<Post>) =
                postsDao.insertPosts(data.map { PostEntity(it.id, it.userId, it.title, it.body) })

            override fun fetchFromLocal(): Flow<List<PostEntity>> = postsDao.getPosts()

            override suspend fun fetchFromRemote(): Response<List<Post>> = apiService.getPosts()

        }.asFlow().flowOn(Dispatchers.IO)
    }

    override fun getPost(postId: Long): Flow<State<PostEntity>> {
        return object : NetworkBoundRepository<PostEntity, Post>() {

            override suspend fun saveRemoteData(data: Post) =
                postsDao.insertPost(PostEntity(data.id, data.userId, data.title, data.body))

            override fun fetchFromLocal(): Flow<PostEntity> = postsDao.getPostById(postId)

            override suspend fun fetchFromRemote(): Response<Post> = apiService.getPost(postId)

        }.asFlow().flowOn(Dispatchers.IO)
    }
}