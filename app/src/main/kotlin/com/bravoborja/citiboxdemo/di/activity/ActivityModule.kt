package com.bravoborja.citiboxdemo.di.activity

import com.bravoborja.citiboxdemo.presentation.main.MainActivity
import com.bravoborja.citiboxdemo.presentation.postdetails.PostDetailsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindPostDetailsActivity(): PostDetailsActivity
}
