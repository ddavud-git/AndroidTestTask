package com.example.androidtesttask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.androidtesttask.R
import com.example.androidtesttask.databinding.FragmentItemDetailBinding
import com.example.androidtesttask.entity.PlaceholderItem
import com.google.android.material.appbar.CollapsingToolbarLayout
import dagger.android.support.DaggerFragment

class CountryDetailFragment : DaggerFragment() {

    private var item: PlaceholderItem? = null


    private var _binding: FragmentItemDetailBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                item = it.getParcelable(ARG_ITEM_ID)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        val rootView = binding.root


        updateContent()
        return rootView
    }

    private fun updateContent() {
        item?.let {
            binding.toolbarLayout?.title = it.name
            binding.countryCode?.text = getString(R.string.country_code,it.code)
            binding.countryName?.text = getString(R.string.country_name,it.name)
            binding.countryCapital?.text = getString(R.string.country_capital,it.capital)
            binding.countryCurrency?.text = getString(R.string.country_currency,it.currency)
            binding.countryNative?.text = getString(R.string.country_native,it.native)
        }
    }

    companion object {

        const val ARG_ITEM_ID = "item_id"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}