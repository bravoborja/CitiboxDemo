package com.bravoborja.citiboxdemo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bravoborja.citiboxdemo.data.local.model.CommentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComments(comments: List<CommentEntity>)

    @Query("SELECT * FROM ${CommentEntity.COMMENTS_TABLE_NAME} WHERE POSTID = :postId")
    fun getComments(postId: Long): Flow<List<CommentEntity>>
}