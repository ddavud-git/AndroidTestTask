package com.example.androidtesttask.repository.country

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.example.CountriesListQuery
import com.example.androidtesttask.cache.dao.CountryDao
import javax.inject.Inject

class CountryRepoImpl @Inject constructor(private val apolloClient: ApolloClient,private val countryDao: CountryDao) : CountryRepository {

    override suspend fun queryCountries(): Response<CountriesListQuery.Data> {
        return apolloClient.query(CountriesListQuery()).await()
    }

}