package com.example.androidtesttask.network

import com.example.CountriesListQuery
import kotlin.reflect.KClass

sealed class ResultStatus{
    class Success(val data: CountriesListQuery.Data) : ResultStatus()
    class ErrorRes(val e: java.lang.Exception) : ResultStatus()
}
