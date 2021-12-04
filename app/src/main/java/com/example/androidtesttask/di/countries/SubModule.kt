package com.example.androidtesttask.di.countries

import com.example.androidtesttask.cache.AppDatabase
import com.example.androidtesttask.cache.dao.CountryDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SubModule {
    @Provides
    fun getString() = "Data "

    @Provides
    fun provideUserDao(db: AppDatabase): CountryDao {
        return db.getCountryDao()
    }
}