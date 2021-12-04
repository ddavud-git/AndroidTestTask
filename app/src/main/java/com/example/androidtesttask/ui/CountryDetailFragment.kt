package com.example.androidtesttask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.androidtesttask.databinding.FragmentItemDetailBinding
import com.example.androidtesttask.placeholder.PlaceholderContent
import com.google.android.material.appbar.CollapsingToolbarLayout
import dagger.android.support.DaggerFragment

class CountryDetailFragment : DaggerFragment() {

    private var item: PlaceholderContent.PlaceholderItem? = null

    lateinit var itemDetailTextView: TextView
    private var toolbarLayout: CollapsingToolbarLayout? = null

    private var _binding: FragmentItemDetailBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                item = PlaceholderContent.ITEM_MAP[it.getString(ARG_ITEM_ID)]
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        val rootView = binding.root

        toolbarLayout = binding.toolbarLayout
        itemDetailTextView = binding.itemDetail

        updateContent()
        return rootView
    }

    private fun updateContent() {
        toolbarLayout?.title = item?.content

        item?.let {
            itemDetailTextView.text = it.details
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