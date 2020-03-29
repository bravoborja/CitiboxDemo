package com.bravoborja.citiboxdemo.di.module

import com.bravoborja.citiboxdemo.data.local.dao.CommentsDao
import com.bravoborja.citiboxdemo.data.local.dao.PostsDao
import com.bravoborja.citiboxdemo.data.local.dao.UsersDao
import com.bravoborja.citiboxdemo.data.remote.ApiService
import com.bravoborja.citiboxdemo.data.repository.CommentsDataRepository
import com.bravoborja.citiboxdemo.data.repository.PostsDataRepository
import com.bravoborja.citiboxdemo.data.repository.UsersDataRepository
import com.bravoborja.citiboxdemo.domain.repository.CommentsRepository
import com.bravoborja.citiboxdemo.domain.repository.PostsRepository
import com.bravoborja.citiboxdemo.domain.repository.UsersRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun providePostsRepository(
        dao: PostsDao,
        api: ApiService
    ): PostsRepository = PostsDataRepository(dao, api)

    @Singleton
    @Provides
    fun provideCommentsRepository(
        dao: CommentsDao,
        api: ApiService
    ): CommentsRepository = CommentsDataRepository(dao, api)

    @Singleton
    @Provides
    fun provideUsersRepository(
        dao: UsersDao,
        api: ApiService
    ): UsersRepository = UsersDataRepository(dao, api)
}