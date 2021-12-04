package com.example.androidtesttask.di.app

import android.app.Application
import androidx.room.Room
import com.example.androidtesttask.cache.AppDatabase
import com.example.androidtesttask.cache.dao.CountryDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, "app.db")
            .fallbackToDestructiveMigration()
            .build()
    }

}