package com.bravoborja.citiboxdemo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bravoborja.citiboxdemo.data.local.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(users: List<UserEntity>)

    @Query("SELECT * FROM ${UserEntity.USERS_TABLE_NAME} WHERE ID = :userId")
    fun getAuthor(userId: Long): Flow<UserEntity>
}
