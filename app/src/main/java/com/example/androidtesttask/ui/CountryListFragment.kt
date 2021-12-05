package com.example.androidtesttask.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtesttask.R
import com.example.androidtesttask.adapters.SimpleItemRecyclerViewAdapter
import com.example.androidtesttask.databinding.FragmentItemListBinding
import com.example.androidtesttask.di.viewmodel.AppViewModelFactory
import com.example.androidtesttask.placeholder.PlaceholderContent
import com.example.androidtesttask.ui.countries.CountriesViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CountryListFragment : DaggerFragment() {

    private var _binding: FragmentItemListBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var providerFactory: AppViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), providerFactory)[CountriesViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val recyclerView: RecyclerView = binding.itemList

        val itemDetailFragmentContainer: View? = view.findViewById(R.id.item_detail_nav_container)


        val onClickListener = View.OnClickListener { itemView ->
            val item = itemView.tag as PlaceholderContent.PlaceholderItem
            val bundle = Bundle()
            bundle.putString(
                CountryDetailFragment.ARG_ITEM_ID,
                item.id
            )
            if (itemDetailFragmentContainer != null) {
                itemDetailFragmentContainer.findNavController()
                    .navigate(R.id.fragment_item_detail, bundle)
            } else {
                itemView.findNavController().navigate(R.id.show_item_detail, bundle)
            }
        }

        val onContextClickListener = View.OnContextClickListener { v ->
            val item = v.tag as PlaceholderContent.PlaceholderItem
            Toast.makeText(
                v.context,
                "Context click of item " + item.id,
                Toast.LENGTH_LONG
            ).show()
            true
        }
        setupRecyclerView(recyclerView, onClickListener, onContextClickListener)
    }

    override fun onResume() {
        super.onResume()

        viewModel.getCountryList()

        viewModel.countries.observe(viewLifecycleOwner, Observer {
            Log.d("network data" , "size ${it.countries}")
        })
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        onClickListener: View.OnClickListener,
        onContextClickListener: View.OnContextClickListener
    ) {

        recyclerView.adapter = SimpleItemRecyclerViewAdapter(
            PlaceholderContent.ITEMS,
            onClickListener,
            onContextClickListener
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}