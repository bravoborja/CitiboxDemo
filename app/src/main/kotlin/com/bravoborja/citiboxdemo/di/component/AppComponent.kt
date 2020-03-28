package com.bravoborja.citiboxdemo.di.component

import android.app.Application
import com.bravoborja.citiboxdemo.CitiboxDemoApp
import com.bravoborja.citiboxdemo.di.activity.ActivityModule
import com.bravoborja.citiboxdemo.di.module.ApiModule
import com.bravoborja.citiboxdemo.di.module.DatabaseModule
import com.bravoborja.citiboxdemo.di.module.RepositoryModule
import com.bravoborja.citiboxdemo.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        DatabaseModule::class,
        ApiModule::class,
        ActivityModule::class,
        ViewModelModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent : AndroidInjector<CitiboxDemoApp> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun create(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(app: CitiboxDemoApp)
}