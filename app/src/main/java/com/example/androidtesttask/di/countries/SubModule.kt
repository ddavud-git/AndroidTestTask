package com.example.androidtesttask.di.countries

import dagger.Module
import dagger.Provides

@Module
class SubModule {
    @Provides
    fun getString () = "Data "
}