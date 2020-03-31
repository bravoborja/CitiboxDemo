package com.bravoborja.citiboxdemo.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bravoborja.citiboxdemo.MainCoroutineRule
import com.bravoborja.citiboxdemo.data.local.dao.UsersDao
import com.bravoborja.citiboxdemo.data.local.model.UserEntity
import com.bravoborja.citiboxdemo.data.remote.ApiService
import com.bravoborja.citiboxdemo.data.remote.model.User
import com.nhaarman.mockito_kotlin.any
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
class UsersDataRepositoryTest {

    private lateinit var repository: UsersDataRepository

    @Mock
    lateinit var usersDao: UsersDao

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
        repository = UsersDataRepository(usersDao, api)
    }

    @Test
    fun `get author by user id load author from db`() {
        runBlocking {
            val users = listOf(User(1L, "pepe", "pepe", "pepe@gmail.com"))
            `when`(usersDao.getAuthor(1L)).thenReturn(flowOf(UserEntity()))
            `when`(api.getUsers()).thenReturn(Response.success(200, users))
            repository.getAuthor(1L).take(1).collect {
                verify(usersDao).getAuthor(any())
                verify(usersDao).getAuthor(any())
            }
        }
    }
}