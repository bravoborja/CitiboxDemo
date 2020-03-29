package com.bravoborja.citiboxdemo.di.module

import android.app.Application
import com.bravoborja.citiboxdemo.data.local.PostsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application) = PostsDatabase.getInstance(application)

    @Singleton
    @Provides
    fun providePostsDao(database: PostsDatabase) = database.getPostsDao()

    @Singleton
    @Provides
    fun provideCommentsDao(database: PostsDatabase) = database.getCommentsDao()

    @Singleton
    @Provides
    fun provideUsersDao(database: PostsDatabase) = database.getUsersDao()
}