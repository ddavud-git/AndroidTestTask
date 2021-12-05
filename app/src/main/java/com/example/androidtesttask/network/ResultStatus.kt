package com.example.androidtesttask.network

sealed class ResultStatus{
    object Idle : ResultStatus()
    object Success : ResultStatus()
    class ErrorRes(val e: java.lang.Exception) : ResultStatus()
    object NetworkError : ResultStatus()
}
