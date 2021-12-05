package com.example.androidtesttask.ui.countries

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.CountriesListQuery
import com.example.androidtesttask.repository.country.CountryRepoImpl
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountriesViewModel @Inject constructor(private val countryRepoImpl: CountryRepoImpl) :
    ViewModel() {
    private val _countries = MutableLiveData<CountriesListQuery.Data>()
    val countries get() = _countries
    fun getCountryList() {
        viewModelScope.launch {
            val res = countryRepoImpl.queryCountries()
            countries.postValue(res.data)
        }
    }
}