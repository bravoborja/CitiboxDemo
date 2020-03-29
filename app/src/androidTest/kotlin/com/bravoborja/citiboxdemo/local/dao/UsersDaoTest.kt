package com.bravoborja.citiboxdemo.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bravoborja.citiboxdemo.data.local.PostsDatabase
import com.bravoborja.citiboxdemo.data.local.dao.UsersDao
import com.bravoborja.citiboxdemo.data.local.model.UserEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class UsersDaoTest {

    private lateinit var usersDao: UsersDao
    private lateinit var database: PostsDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        database = Room.inMemoryDatabaseBuilder(
            context,
            PostsDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        usersDao = database.getUsersDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertUsersAndGetAuthorById() {
        runBlocking {
            val users = listOf(UserEntity(1L, "pepe", "pepe", "pepe@gmail.com"))
            usersDao.insertUsers(users)
            usersDao.getAuthor(1L).take(1).collect {
                assertEquals(it.id, 1L)
                assertEquals(it.name, "pepe")
            }
        }
    }
}