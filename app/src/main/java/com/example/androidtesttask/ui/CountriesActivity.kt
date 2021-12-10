package com.example.androidtesttask.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.androidtesttask.R
import com.example.androidtesttask.databinding.ActivityItemDetailBinding
import dagger.android.support.DaggerAppCompatActivity

class CountriesActivity : DaggerAppCompatActivity() {

    private val itemDetailFragment = CountryDetailFragment()

    private val countryListFragment = CountryListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null)
            createParentFragment()
    }

    private fun getCurrentFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.nav_host_fragment_item_detail)
    }


    private fun createParentFragment(
    ) {
        addFragment(countryListFragment)
    }


    fun replaceChildFragment(
        holderId: Int
    ) {
        replaceFragment(holderId, itemDetailFragment)
    }

    fun addChildFragment() {
        addFragment(itemDetailFragment)
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.nav_host_fragment_item_detail, fragment)
            .addToBackStack(fragment.tag)
            .commit()
    }

    private fun replaceFragment(holderId: Int = 0, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(holderId, fragment)
            .commit()
    }

    override fun onBackPressed() {
        if (getCurrentFragment() is CountryListFragment)
           finish()
        else {
            super.onBackPressed()
        }

    }

}