package com.bravoborja.citiboxdemo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bravoborja.citiboxdemo.data.local.dao.CommentsDao
import com.bravoborja.citiboxdemo.data.local.dao.PostsDao
import com.bravoborja.citiboxdemo.data.local.dao.UsersDao
import com.bravoborja.citiboxdemo.data.local.model.CommentEntity
import com.bravoborja.citiboxdemo.data.local.model.PostEntity
import com.bravoborja.citiboxdemo.data.local.model.UserEntity

@Database(
    entities = [PostEntity::class, CommentEntity::class, UserEntity::class],
    version = DatabaseMigrations.DB_VERSION
)
abstract class PostsDatabase : RoomDatabase() {

    abstract fun getPostsDao(): PostsDao

    abstract fun getUsersDao(): UsersDao

    abstract fun getCommentsDao(): CommentsDao

    companion object {
        private const val DB_NAME = "posts_database"

        @Volatile
        private var INSTANCE: PostsDatabase? = null

        fun getInstance(context: Context): PostsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PostsDatabase::class.java,
                    DB_NAME
                ).addMigrations(*DatabaseMigrations.MIGRATIONS)
                    .build()
                INSTANCE = instance
                return instance
            }
        }

    }
}