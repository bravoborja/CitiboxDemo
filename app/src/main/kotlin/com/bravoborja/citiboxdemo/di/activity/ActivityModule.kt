package com.bravoborja.citiboxdemo.di.activity

import com.bravoborja.citiboxdemo.presentation.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}
