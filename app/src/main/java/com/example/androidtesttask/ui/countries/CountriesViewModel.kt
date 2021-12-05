package com.example.androidtesttask.ui.countries

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloNetworkException
import com.example.CountriesListQuery
import com.example.androidtesttask.Constants
import com.example.androidtesttask.cache.dao.CountryDao
import com.example.androidtesttask.network.ResultStatus
import com.example.androidtesttask.repository.country.CountryRepoImpl
import com.example.androidtesttask.util.DataConverter.convertDataToCountry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CountriesViewModel @Inject constructor(
    private val countryRepoImpl: CountryRepoImpl,
    private val countryDao: CountryDao
) :
    ViewModel() {
    private val _networkResMutableLiveData = MutableLiveData<ResultStatus>()

    val networkResLiveData: LiveData<ResultStatus> get() = _networkResMutableLiveData

    val cache = countryDao.getList()

    fun getCountryList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val networkRes: ResultStatus = try {
                    val res = countryRepoImpl.queryCountries()
                    saveToCache(res)
                } catch (e: ApolloNetworkException) {
                    ResultStatus.NetworkError
                } catch (e: Exception) {
                    Log.e(Constants.LOG_IO_DATA_TAG, e.stackTraceToString())
                    ResultStatus.ErrorRes(e)
                }
                _networkResMutableLiveData.postValue(networkRes)
            }
        }
    }

    private fun saveToCache(res: Response<CountriesListQuery.Data>): ResultStatus {
        val data = res.data
        return try {
            val dataToInsert = data!!.convertDataToCountry()
            val ids = countryDao.insert(dataToInsert)
            Log.d(Constants.LOG_IO_DATA_TAG, "Response data saved to cache successfully id -> $ids")
            ResultStatus.Success
        } catch (e: java.lang.Exception) {
            ResultStatus.SaveCacheFail(e)
        }
    }


}