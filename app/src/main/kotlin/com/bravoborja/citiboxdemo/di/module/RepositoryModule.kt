package com.bravoborja.citiboxdemo.di.module

import com.bravoborja.citiboxdemo.data.local.dao.PostsDao
import com.bravoborja.citiboxdemo.data.remote.ApiService
import com.bravoborja.citiboxdemo.data.repository.PostsDataRepository
import com.bravoborja.citiboxdemo.domain.repository.PostsRepository
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
        api: ApiService,
        dao: PostsDao
    ): PostsRepository = PostsDataRepository(dao, api)
}