package com.bravoborja.citiboxdemo.presentation.postdetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bravoborja.citiboxdemo.MainCoroutineRule
import com.bravoborja.citiboxdemo.common.State
import com.bravoborja.citiboxdemo.domain.model.CommentModel
import com.bravoborja.citiboxdemo.domain.model.UserModel
import com.bravoborja.citiboxdemo.domain.repository.CommentsRepository
import com.bravoborja.citiboxdemo.domain.repository.UsersRepository
import com.bravoborja.citiboxdemo.domain.usecase.GetAuthorUseCase
import com.bravoborja.citiboxdemo.domain.usecase.GetCommentsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class PostDetailsViewModelTest {

    lateinit var viewModel: PostDetailsViewModel
    lateinit var getAuthorUseCase: GetAuthorUseCase
    lateinit var getCommentsUseCase: GetCommentsUseCase

    @Mock
    lateinit var usersRepository: UsersRepository

    @Mock
    lateinit var commentsRepository: CommentsRepository

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        getAuthorUseCase = GetAuthorUseCase(usersRepository)
        getCommentsUseCase = GetCommentsUseCase(commentsRepository)
        viewModel = PostDetailsViewModel(getAuthorUseCase, getCommentsUseCase)
    }

    @Test
    fun `get author should show loading`() {
        `when`(usersRepository.getAuthor(1L)).thenReturn(flowOf(State.loading()))
        viewModel.getAuthor(1L)
        val authorLiveData = viewModel.authorLiveData.value
        assert(authorLiveData is State.Loading)
    }

    @Test
    fun `get author should show error`() {
        `when`(usersRepository.getAuthor(1L)).thenReturn(flowOf(State.error("Error")))
        viewModel.getAuthor(1L)
        val authorLiveData = viewModel.authorLiveData.value
        assert(authorLiveData is State.Error)
        assert((authorLiveData as State.Error).message == "Error")
    }

    @Test
    fun `get author should show success`() {
        val userSuccess: State<UserModel?> = State.success(
            UserModel(
                1L,
                "pepe",
                "pepe",
                "pepe@gmail.com"
            )
        )
        `when`(usersRepository.getAuthor(1L)).thenReturn(flowOf(userSuccess))
        viewModel.getAuthor(1L)
        val authorLiveData = viewModel.authorLiveData.value
        assert(authorLiveData is State.Success)
        assert((authorLiveData as State.Success).data?.id == 1L)
        assert(authorLiveData.data?.name == "pepe")
        assert(authorLiveData.data?.name == "pepe")
        assert(authorLiveData.data?.email == "pepe@gmail.com")
    }

    @Test
    fun `get comments should show loading`() {
        `when`(commentsRepository.getComments(1L)).thenReturn(flowOf(State.loading()))
        viewModel.getComments(1L)
        val commentsLiveData = viewModel.commentsLiveData.value
        assert(commentsLiveData is State.Loading)
    }

    @Test
    fun `get comments should show error`() {
        `when`(commentsRepository.getComments(1L)).thenReturn(flowOf(State.error("Error")))
        viewModel.getComments(1L)
        val commentsLiveData = viewModel.commentsLiveData.value
        assert(commentsLiveData is State.Error)
        assert((commentsLiveData as State.Error).message == "Error")
    }

    @Test
    fun `get comments should show success`() {
        val commentsSuccess: State<List<CommentModel>> = State.success(
            listOf(
                CommentModel(
                    1L,
                    1L,
                    "pepe",
                    "pepe@gmail.com",
                    "body"
                )
            )
        )
        `when`(commentsRepository.getComments(1L)).thenReturn(flowOf(commentsSuccess))
        viewModel.getComments(1L)
        val commentsLiveData = viewModel.commentsLiveData.value
        assert(commentsLiveData is State.Success)
        assert((commentsLiveData as State.Success).data.size == 1)
        assert(commentsLiveData.data[0].id == 1L)
        assert(commentsLiveData.data[0].postId == 1L)
        assert(commentsLiveData.data[0].name == "pepe")
        assert(commentsLiveData.data[0].email == "pepe@gmail.com")
        assert(commentsLiveData.data[0].body == "body")
    }
}