package com.example.androidtesttask.repository.country

import com.apollographql.apollo.api.Response
import com.example.CountriesListQuery
import com.example.androidtesttask.entity.Country
import com.example.androidtesttask.entity.PlaceholderItem

interface CountryRepository {

    suspend fun queryCountries():Response<CountriesListQuery.Data>
    fun insert(dataToInsert: List<Country>):List<Long>

}