package com.bravoborja.citiboxdemo.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bravoborja.citiboxdemo.data.local.model.PostEntity.Companion.POSTS_TABLE_NAME

@Entity(tableName = POSTS_TABLE_NAME)
data class PostEntity(
    @PrimaryKey
    var id: Long? = 0,
    var userId: Long? = 0,
    var title: String? = null,
    var body: String? = null
) {
    companion object {
        const val POSTS_TABLE_NAME = "posts"
    }
}