package com.example.androidtesttask.repository.country

import com.apollographql.apollo.api.Response
import com.example.CountriesListQuery

interface CountryRepository {

    suspend fun queryCountries():Response<CountriesListQuery.Data>

}