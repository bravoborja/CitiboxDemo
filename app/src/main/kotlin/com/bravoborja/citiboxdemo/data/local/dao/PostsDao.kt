package com.bravoborja.citiboxdemo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bravoborja.citiboxdemo.data.local.model.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPosts(posts: List<PostEntity>)

    @Query("SELECT * FROM ${PostEntity.POSTS_TABLE_NAME} WHERE ID = :postId")
    fun getPostById(postId: Long): Flow<PostEntity>

    @Query("SELECT * FROM ${PostEntity.POSTS_TABLE_NAME}")
    fun getPosts(): Flow<List<PostEntity>>
}