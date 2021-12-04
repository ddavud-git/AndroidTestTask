package com.example.androidtesttask.di.countries

import com.example.androidtesttask.ui.CountryDetailFragment
import com.example.androidtesttask.ui.CountryListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilders {

    @ContributesAndroidInjector
    abstract fun contributeCountryDetailsFragment(): CountryDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeCountryListFragment(): CountryListFragment

}