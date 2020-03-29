package com.bravoborja.citiboxdemo.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bravoborja.citiboxdemo.data.local.model.CommentEntity
import com.bravoborja.citiboxdemo.data.local.model.UserEntity

object DatabaseMigrations {
    const val DB_VERSION = 2

    val MIGRATIONS: Array<Migration>
        get() = arrayOf(migration12())

    private fun migration12(): Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "CREATE TABLE  ${CommentEntity.COMMENTS_TABLE_NAME} (`id` INTEGER, `postId` INTEGER, `name` TEXT, `email` TEXT, `body` TEXT, " +
                        "PRIMARY KEY(`id`))"
            )
            database.execSQL(
                "CREATE TABLE ${UserEntity.USERS_TABLE_NAME} (`id` INTEGER, `name` TEXT, `username` TEXT, `email` TEXT, " +
                        "PRIMARY KEY(`id`))"
            )
        }
    }
}
