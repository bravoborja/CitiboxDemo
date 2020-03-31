package com.bravoborja.citiboxdemo.presentation.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bravoborja.citiboxdemo.MainCoroutineRule
import com.bravoborja.citiboxdemo.common.State
import com.bravoborja.citiboxdemo.domain.model.PostModel
import com.bravoborja.citiboxdemo.domain.repository.PostsRepository
import com.bravoborja.citiboxdemo.domain.usecase.GetPostsUseCase
import com.bravoborja.citiboxdemo.presentation.main.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MainViewModelTest {

    lateinit var mainViewModel: MainViewModel

    lateinit var getPostsUseCase: GetPostsUseCase

    @Mock
    lateinit var repository: PostsRepository

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        getPostsUseCase = GetPostsUseCase(repository)
        mainViewModel = MainViewModel(getPostsUseCase)
    }

    @Test
    fun `get posts should show loading`() {
        `when`(repository.getPosts()).thenReturn(flowOf(State.loading()))
        mainViewModel.getPosts()
        val postsLiveData = mainViewModel.postsLiveData.value
        assert(postsLiveData is State.Loading)
    }

    @Test
    fun `get posts should show error`() {
        `when`(repository.getPosts()).thenReturn(flowOf(State.error("Error")))
        mainViewModel.getPosts()
        val postsLiveData = mainViewModel.postsLiveData.value
        assert(postsLiveData is State.Error)
        assert((postsLiveData as State.Error).message == "Error")
    }

    @Test
    fun `get posts should show success`() {
        val postList = listOf(
            PostModel(1L, 1L, "title", "body")
        )
        `when`(repository.getPosts()).thenReturn(flowOf(State.success(postList)))
        mainViewModel.getPosts()
        val postsLiveData = mainViewModel.postsLiveData.value
        assert(postsLiveData is State.Success)
        assert((postsLiveData as State.Success).data.size == 1)
        assert(postsLiveData.data[0].id == 1L)
        assert(postsLiveData.data[0].userId == 1L)
        assert(postsLiveData.data[0].title == "title")
        assert(postsLiveData.data[0].body == "body")
    }
}