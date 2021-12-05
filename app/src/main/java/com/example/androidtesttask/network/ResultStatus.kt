package com.example.androidtesttask.network

import com.example.CountriesListQuery
import com.example.androidtesttask.entity.PlaceholderItem
import kotlin.reflect.KClass

sealed class ResultStatus{
    class Success() : ResultStatus()
    class ErrorRes(val e: java.lang.Exception) : ResultStatus()
    class NetworkError : ResultStatus()
}
