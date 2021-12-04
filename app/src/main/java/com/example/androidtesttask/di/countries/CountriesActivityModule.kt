package com.example.androidtesttask.di.countries

import com.example.androidtesttask.di.scopes.CountriesScope
import com.example.androidtesttask.ui.CountriesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CountriesActivityModule {
    @CountriesScope
    @ContributesAndroidInjector(modules = [ViewModelModule::class, FragmentBuilders::class, SubModule::class])
    abstract fun contributeCountriesActivity(): CountriesActivity
}