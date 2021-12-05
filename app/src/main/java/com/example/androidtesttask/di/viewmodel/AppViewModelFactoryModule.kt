package com.example.androidtesttask.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Provider

@Module
class AppViewModelFactoryModule {
    @Provides
    @Inject
    fun viewModelFactory(providerMap: Map<Class<out ViewModel>, Provider<ViewModel>>): ViewModelProvider.Factory {
        return AppViewModelFactory(providerMap)
    }
}
