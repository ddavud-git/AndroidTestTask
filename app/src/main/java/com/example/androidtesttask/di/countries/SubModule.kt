package com.example.androidtesttask.di.countries

import com.apollographql.apollo.ApolloClient
import com.example.androidtesttask.cache.AppDatabase
import com.example.androidtesttask.cache.dao.CountryDao
import com.example.androidtesttask.di.scopes.CountriesScope
import com.example.androidtesttask.repository.country.CountryRepoImpl
import com.example.androidtesttask.repository.country.CountryRepository
import dagger.Module
import dagger.Provides

@Module
class SubModule {

    @CountriesScope
    @Provides
    fun provideUserDao(db: AppDatabase): CountryDao {
        return db.getCountryDao()
    }

    @CountriesScope
    @Provides
    fun provideRepoImp(apolloClient: ApolloClient, countryDao: CountryDao): CountryRepository {
        return CountryRepoImpl(apolloClient, countryDao)
    }

}