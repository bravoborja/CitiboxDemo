package com.bravoborja.citiboxdemo.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bravoborja.citiboxdemo.data.local.PostsDatabase
import com.bravoborja.citiboxdemo.data.local.dao.PostsDao
import com.bravoborja.citiboxdemo.data.local.model.PostEntity
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
class PostsDaoTest {

    private lateinit var postsDao: PostsDao
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
        postsDao = database.getPostsDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetPosts() {
        runBlocking {
            val post = PostEntity()
            postsDao.insertPost(post)
            postsDao.getPosts().take(1).collect {
                assertEquals(it.size, 1)
            }
        }
    }
}