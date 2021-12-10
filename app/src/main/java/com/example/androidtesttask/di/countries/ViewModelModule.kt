package com.example.androidtesttask.di.countries

import androidx.lifecycle.ViewModel
import com.example.androidtesttask.di.scopes.CountriesScope
import com.example.androidtesttask.di.scopes.ViewModelKey
import com.example.androidtesttask.ui.viewmodel.CountriesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @CountriesScope
    @Binds
    @IntoMap
    @ViewModelKey(CountriesViewModel::class)
    abstract fun bindCountriesViewModel(countriesViewModel: CountriesViewModel):ViewModel

}