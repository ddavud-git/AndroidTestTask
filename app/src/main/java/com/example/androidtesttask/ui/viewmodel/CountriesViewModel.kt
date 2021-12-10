package com.example.androidtesttask.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.exception.ApolloNetworkException
import com.example.androidtesttask.Constants
import com.example.androidtesttask.entity.PlaceholderItem
import com.example.androidtesttask.network.ResultStatus
import com.example.androidtesttask.repository.country.CountryRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CountriesViewModel @Inject constructor(
    private val countryRepoImpl: CountryRepoImpl
) :
    ViewModel() {

    val itemDetailsLiveData= MutableLiveData<PlaceholderItem>()

    private val _networkResMutableLiveData = MutableLiveData<ResultStatus>()

    val networkResLiveData: LiveData<ResultStatus> get() = _networkResMutableLiveData

    val cache = countryRepoImpl.cache

    fun getCountryList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val networkRes: ResultStatus = try {
                    val res = countryRepoImpl.queryCountries()
                    countryRepoImpl.saveToCache(res)
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

    fun postItem(item: PlaceholderItem) {
        itemDetailsLiveData.postValue(item)
    }


}