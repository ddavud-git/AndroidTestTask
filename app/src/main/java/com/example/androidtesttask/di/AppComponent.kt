package com.example.androidtesttask.di

import com.example.androidtesttask.Application
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
        AppViewModelFactoryModule::class,
        CountriesActivityModule::class
    ]
)

interface AppComponent : AndroidInjector<Application> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}