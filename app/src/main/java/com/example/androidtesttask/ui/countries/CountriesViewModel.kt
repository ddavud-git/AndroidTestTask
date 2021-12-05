package com.example.androidtesttask.ui.countries

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.CountriesListQuery
import com.example.androidtesttask.Constants
import com.example.androidtesttask.network.ResultStatus
import com.example.androidtesttask.repository.country.CountryRepoImpl
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountriesViewModel @Inject constructor(private val countryRepoImpl: CountryRepoImpl) :
    ViewModel() {
    private val _countries = MutableLiveData<ResultStatus>()
    val countries get() = _countries
    fun getCountryList() {
        viewModelScope.launch {
            var result: ResultStatus? = null
            try {
                val res = countryRepoImpl.queryCountries()
                res.data?.let { result = ResultStatus.Success(it) }
            } catch (e: Exception) {
                Log.e(Constants.LOG_DATA_TAG, e.stackTraceToString())
               result =  ResultStatus.ErrorRes(e)
            } finally {
                countries.postValue(result)
            }

        }
    }
}