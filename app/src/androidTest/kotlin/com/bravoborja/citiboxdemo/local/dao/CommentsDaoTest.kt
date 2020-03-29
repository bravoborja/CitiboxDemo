package com.bravoborja.citiboxdemo.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bravoborja.citiboxdemo.data.local.PostsDatabase
import com.bravoborja.citiboxdemo.data.local.dao.CommentsDao
import com.bravoborja.citiboxdemo.data.local.model.CommentEntity
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
class CommentsDaoTest {

    private lateinit var commentsDao: CommentsDao
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
        commentsDao = database.getCommentsDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetComments() {
        runBlocking {
            val comments = listOf(
                CommentEntity(1L, 1L, "pepe", "pepe@gmail.com", "body"),
                CommentEntity(2L, 1L, "juan", "juan@gmail.com", "body2")
            )
            commentsDao.insertComments(comments)
            commentsDao.getComments(1).take(1).collect {
                assertEquals(it.size, 2)
            }
        }
    }
}
