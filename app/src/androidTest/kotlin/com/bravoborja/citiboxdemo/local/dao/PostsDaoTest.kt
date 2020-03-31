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
    fun insertOnePostAndGetPosts() {
        runBlocking {
            val post = PostEntity(1L, 1L, "prueba", "body")
            postsDao.insertPost(post)
            postsDao.getPosts().take(1).collect {
                assertEquals(it.size, 1)
                assertEquals(it[0].id, 1L)
                assertEquals(it[0].userId, 1L)
                assertEquals(it[0].title, "prueba")
                assertEquals(it[0].body, "body")
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertOnePostAndGetPost() {
        runBlocking {
            val post = PostEntity(1L, 1L, "prueba", "body")
            postsDao.insertPost(post)
            postsDao.getPostById(1L).take(1).collect {
                assertEquals(it.id, 1L)
                assertEquals(it.userId, 1L)
                assertEquals(it.title, "prueba")
                assertEquals(it.body, "body")
            }
        }
    }


    @Test
    @Throws(Exception::class)
    fun insertMultiplePostsAndGetPosts() {
        runBlocking {
            val posts = listOf(
                PostEntity(1L, 1L, "prueba", "body"),
                PostEntity(2L, 1L, "prueba2", "body2")
            )
            postsDao.insertPosts(posts)
            postsDao.getPosts().take(1).collect {
                assertEquals(it.size, 2)
                assertEquals(it[1].id, 2L)
                assertEquals(it[1].userId, 1L)
                assertEquals(it[1].title, "prueba2")
                assertEquals(it[1].body, "body2")
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertMultiplePostsAndGetPost() {
        runBlocking {
            val posts = listOf(
                PostEntity(1L, 1L, "prueba", "body"),
                PostEntity(2L, 1L, "prueba2", "body2")
            )
            postsDao.insertPosts(posts)
            postsDao.getPostById(1L).take(1).collect {
                assertEquals(it.id, 1L)
                assertEquals(it.userId, 1L)
                assertEquals(it.title, "prueba")
                assertEquals(it.body, "body")
            }
        }
    }
}