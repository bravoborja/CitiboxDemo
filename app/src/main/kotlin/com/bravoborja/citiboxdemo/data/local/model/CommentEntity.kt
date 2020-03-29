package com.bravoborja.citiboxdemo.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bravoborja.citiboxdemo.data.local.model.CommentEntity.Companion.COMMENTS_TABLE_NAME

@Entity(tableName = COMMENTS_TABLE_NAME)
data class CommentEntity(
    @PrimaryKey
    var id: Long? = 0,
    var postId: Long? = 0,
    var name: String? = null,
    var email: String? = null,
    var body: String? = null
) {
    companion object {
        const val COMMENTS_TABLE_NAME = "comments"
    }
}