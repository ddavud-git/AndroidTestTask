package com.example.androidtesttask.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.androidtesttask.Constants
import com.example.androidtesttask.R
import com.example.androidtesttask.databinding.FragmentItemDetailBinding
import com.example.androidtesttask.di.viewmodel.AppViewModelFactory
import com.example.androidtesttask.entity.PlaceholderItem
import com.example.androidtesttask.ui.viewmodel.CountriesViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CountryDetailFragment : DaggerFragment() {


    private var _binding: FragmentItemDetailBinding? = null


    @Inject
    lateinit var providerFactory: AppViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), providerFactory)[CountriesViewModel::class.java]
    }

    private val binding get() = _binding!!

    init {
        Log.d(Constants.LOG_NAVIGATION_TAG,"On init detail fragment")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(Constants.LOG_NAVIGATION_TAG,"On Create detail fragment")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        val rootView = binding.root

        viewModel.itemDetailsLiveData.observe(viewLifecycleOwner, {
            updateContent(it)
        })

        return rootView
    }



    private fun updateContent(item: PlaceholderItem) {
        item.let {
            binding.toolbarLayout?.title = it.name
            binding.countryCode.text = getString(R.string.country_code, it.code)
            binding.countryName.text = getString(R.string.country_name, it.name)
            binding.countryCapital.text = getString(R.string.country_capital, it.capital)
            binding.countryCurrency.text = getString(R.string.country_currency, it.currency)
            binding.countryNative.text = getString(R.string.country_native, it.native)
            binding.countryContinent.text = getString(R.string.country_continent, it.continent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}