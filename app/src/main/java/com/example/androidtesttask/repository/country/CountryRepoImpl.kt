package com.example.androidtesttask.repository.country

import android.content.SharedPreferences
import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.example.CountriesListQuery
import com.example.androidtesttask.Constants
import com.example.androidtesttask.cache.dao.CountryDao
import com.example.androidtesttask.entity.Country
import com.example.androidtesttask.network.ResultStatus
import com.example.androidtesttask.util.DataConverter.convertDataToCountry
import javax.inject.Inject

class CountryRepoImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val dao: CountryDao,
    private val sharedPreferences: SharedPreferences
) : CountryRepository {

    val cache = dao.getList()

    override suspend fun queryCountries(): Response<CountriesListQuery.Data> {
        return apolloClient.query(CountriesListQuery()).await()
    }

    override fun insert(dataToInsert: List<Country>): List<Long> {
        return dao.insert(dataToInsert)
    }


    fun saveToCache(res: Response<CountriesListQuery.Data>): ResultStatus {
        val data = res.data
        return try {
            val dataToInsert = data!!.convertDataToCountry()
            val ids = insert(dataToInsert)
            Log.d(Constants.LOG_IO_DATA_TAG, "Response data saved to cache successfully id -> $ids")
            setDataIsFetched()
            ResultStatus.Success
        } catch (e: java.lang.Exception) {
            ResultStatus.SaveCacheFail(e)
        }
    }


    private fun setDataIsFetched() {
        sharedPreferences.edit().putBoolean(Constants.IS_DATA_FETCHED, true).apply()
    }

    private fun IsDataFetched(): Boolean {
        return sharedPreferences.getBoolean(Constants.IS_DATA_FETCHED, false)
    }

}