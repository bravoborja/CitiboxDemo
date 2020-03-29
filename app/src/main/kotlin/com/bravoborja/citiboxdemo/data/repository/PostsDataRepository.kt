package com.bravoborja.citiboxdemo.data.repository

import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.bravoborja.citiboxdemo.BuildConfig
import com.bravoborja.citiboxdemo.common.State
import com.bravoborja.citiboxdemo.data.local.dao.PostsDao
import com.bravoborja.citiboxdemo.data.local.model.PostEntity
import com.bravoborja.citiboxdemo.data.remote.ApiService
import com.bravoborja.citiboxdemo.data.remote.model.Post
import com.bravoborja.citiboxdemo.domain.model.PostModel
import com.bravoborja.citiboxdemo.domain.repository.PostsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
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
    override fun getPosts(): Flow<State<List<PostModel>>> {
        return object : NetworkBoundRepository<List<PostModel>, List<Post>>() {

            override suspend fun saveRemoteData(data: List<Post>) {
                postsDao.insertPosts(data.map {
                    PostEntity(
                        it.id,
                        it.userId,
                        it.title,
                        it.body,
                        getImageUrl()
                    )
                })
            }

            override fun fetchFromLocal(): Flow<List<PostModel>> {
                val transform: suspend (value: List<PostEntity>) -> Flow<List<PostModel>> = {
                    val posts = it.map { post ->
                        PostModel(
                            post.id,
                            post.userId,
                            post.title,
                            post.body,
                            post.imageUrl
                        )
                    }
                    flowOf(posts)
                }
                return postsDao.getPosts().flatMapConcat(transform).asLiveData().asFlow()
            }

            override suspend fun fetchFromRemote(): Response<List<Post>> = apiService.getPosts()

        }.asFlow().flowOn(Dispatchers.IO)
    }

    override fun getPost(postId: Long): Flow<State<PostModel>> {
        return object : NetworkBoundRepository<PostModel, Post>() {

            override suspend fun saveRemoteData(data: Post) {
                postsDao.insertPost(
                    PostEntity(
                        data.id,
                        data.userId,
                        data.title,
                        data.body,
                        getImageUrl()
                    )
                )
            }

            override fun fetchFromLocal(): Flow<PostModel> {
                val transform: suspend (value: PostEntity) -> Flow<PostModel> = {
                    flowOf(PostModel(it.id, it.userId, it.title, it.body, it.imageUrl))
                }
                return postsDao.getPostById(postId).flatMapConcat(transform).asLiveData().asFlow()
            }

            override suspend fun fetchFromRemote(): Response<Post> = apiService.getPost(postId)

        }.asFlow().flowOn(Dispatchers.IO)
    }

    private fun getImageUrl() = BuildConfig.RANDOM_IMAGE_URL + getRandom()

    private fun getRandom() = (0..300).random()
}