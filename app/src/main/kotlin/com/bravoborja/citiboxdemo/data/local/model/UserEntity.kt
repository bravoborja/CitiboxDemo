package com.bravoborja.citiboxdemo.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bravoborja.citiboxdemo.data.local.model.UserEntity.Companion.USERS_TABLE_NAME

@Entity(tableName = USERS_TABLE_NAME)
data class UserEntity(
    @PrimaryKey
    var id: Long? = 0,
    var name: String? = null,
    var username: String? = null,
    var email: String? = null
) {
    companion object {
        const val USERS_TABLE_NAME = "users"
    }
}