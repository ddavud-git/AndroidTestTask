package com.example.androidtesttask.di

import android.app.Application
import com.example.androidtesttask.App
import com.example.androidtesttask.di.app.AppModule
import com.example.androidtesttask.di.countries.CountriesActivityModule
import com.example.androidtesttask.di.viewmodel.AppViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        AppViewModelFactoryModule::class,
        CountriesActivityModule::class
    ]
)

interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder
        fun build(): AppComponent
    }
}