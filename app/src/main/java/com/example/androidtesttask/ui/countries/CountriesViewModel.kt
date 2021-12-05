package com.example.androidtesttask.ui.countries

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.exception.ApolloNetworkException
import com.example.androidtesttask.Constants
import com.example.androidtesttask.cache.dao.CountryDao
import com.example.androidtesttask.entity.Country
import com.example.androidtesttask.network.ResultStatus
import com.example.androidtesttask.repository.country.CountryRepoImpl
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
                var networkRes: ResultStatus = ResultStatus.Idle
                try {
                    val res = countryRepoImpl.queryCountries()
                    val data = res.data
                    data?.let {

                        val dataToInsert = data.countries.map { country ->
                            Country(
                                country.code,
                                country.name,
                                country.capital ?: "",
                                country.native_,
                                country.currency ?: "",
                                country.continent.name
                            )
                        }
                        val ids= countryDao.insert(dataToInsert)
                        Log.d(Constants.LOG_IO_DATA_TAG,"Response data saved to cache successfully id -> $ids")
                        networkRes = ResultStatus.Success
                    }


                } catch (e: ApolloNetworkException) {
                    networkRes = ResultStatus.NetworkError
                } catch (e: Exception) {
                    Log.e(Constants.LOG_IO_DATA_TAG, e.stackTraceToString())
                    networkRes = ResultStatus.ErrorRes(e)
                } finally {
                    _networkResMutableLiveData.postValue(networkRes)
                }
            }

        }
    }
}