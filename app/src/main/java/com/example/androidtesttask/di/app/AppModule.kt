package com.example.androidtesttask.di.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.apollographql.apollo.ApolloClient
import com.example.androidtesttask.Constants
import com.example.androidtesttask.Constants.DATABASE_NAME
import com.example.androidtesttask.Constants.DEFAULT_SHARED_PREFERENCES
import com.example.androidtesttask.cache.AppDatabase
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }


    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder().build()


    @Singleton
    @Provides
    fun getApolloClient(okHttpClient: OkHttpClient): ApolloClient {
        return ApolloClient.builder()
            .serverUrl(Constants.DOMAIN_NAME)
            .okHttpClient(okHttpClient)
            .build()
    }


    @Singleton
    @Provides
    fun provideDataPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences(DEFAULT_SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

}