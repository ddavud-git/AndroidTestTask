package com.example.androidtesttask.network

sealed class ResultStatus{
    class SaveCacheFail(val e:Exception) : ResultStatus()
    object Success : ResultStatus()
    class ErrorRes(val e: java.lang.Exception) : ResultStatus()
    object NetworkError : ResultStatus()
}
