package com.example.androidtesttask.util

import com.example.CountriesListQuery
import com.example.androidtesttask.entity.Country
import com.example.androidtesttask.entity.PlaceholderItem

object DataConverter {
    fun CountriesListQuery.Data.convertDataToCountry(): List<Country> {
        val countryList = this.countries.map { country ->
            Country(
                country.code,
                country.name,
                country.capital ?: "",
                country.native_,
                country.currency ?: "",
                country.continent.name
            )
        }
        return countryList
    }

    fun List<Country>.convertDataToPlaceHolder(): List<PlaceholderItem> {
        val values =
            this.map { country ->
                PlaceholderItem(
                    country.code,
                    country.name,
                    country.capital,
                    country.native,
                    country.currency,
                    country.continent
                )
            }
        return values
    }
}