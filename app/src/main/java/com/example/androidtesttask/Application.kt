package com.example.androidtesttask

import com.example.androidtesttask.di.AppComponent
import com.example.androidtesttask.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class Application:DaggerApplication() {
    private var appComponent: AppComponent? = null

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build().apply {
            appComponent = this
        }
    }
}