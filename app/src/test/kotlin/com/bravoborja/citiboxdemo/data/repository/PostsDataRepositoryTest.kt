package com.bravoborja.citiboxdemo.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bravoborja.citiboxdemo.MainCoroutineRule
import com.bravoborja.citiboxdemo.data.local.dao.PostsDao
import com.bravoborja.citiboxdemo.data.local.model.PostEntity
import com.bravoborja.citiboxdemo.data.remote.ApiService
import com.bravoborja.citiboxdemo.data.remote.model.Post
import com.nhaarman.mockito_kotlin.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
class PostsDataRepositoryTest {

    private lateinit var repository: PostsDataRepository

    @Mock
    lateinit var postsDao: PostsDao

    @Mock
    lateinit var api: ApiService

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        repository = PostsDataRepository(postsDao, api)
    }

    @Test
    fun `get posts load posts from db`() {
        runBlocking {
            val posts = listOf(Post(1L, 1L, "title", "body"))
            `when`(postsDao.getPosts()).thenReturn(flowOf(listOf(PostEntity())))
            `when`(api.getPosts()).thenReturn(Response.success(200, posts))
            repository.getPosts().take(1).collect {
                verify(postsDao).getPosts()
                verify(postsDao).getPosts()
            }
        }
    }
}