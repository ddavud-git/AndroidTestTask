package com.example.androidtesttask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtesttask.R
import com.example.androidtesttask.adapters.SimpleItemRecyclerViewAdapter
import com.example.androidtesttask.databinding.FragmentItemListBinding
import com.example.androidtesttask.di.viewmodel.AppViewModelFactory
import com.example.androidtesttask.entity.PlaceholderItem
import com.example.androidtesttask.network.ResultStatus
import com.example.androidtesttask.ui.countries.CountriesViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CountryListFragment : DaggerFragment() {

    private var _binding: FragmentItemListBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: SimpleItemRecyclerViewAdapter

    @Inject
    lateinit var providerFactory: AppViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), providerFactory)[CountriesViewModel::class.java]
    }


    init {
        lifecycleScope.launchWhenStarted {
            try {
                viewModel.getCountryList()
            } finally {
                if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                    viewModel.countries.observe(viewLifecycleOwner, Observer {

                        when (it) {
                            is ResultStatus.Success -> adapter.updateList(
                                it.data
                            )
                            is ResultStatus.ErrorRes -> showErrorMessage(it.e)
                        }
                    })
                }
            }
        }
    }

    private fun showErrorMessage(e: Exception) {
        Toast.makeText(requireContext(), "Error ${e.printStackTrace()}", Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = SimpleItemRecyclerViewAdapter()
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
            val item = itemView.tag as PlaceholderItem
            val bundle = Bundle()
            bundle.putParcelable(
                CountryDetailFragment.ARG_ITEM_ID,
                item
            )
            if (itemDetailFragmentContainer != null) {
                itemDetailFragmentContainer.findNavController()
                    .navigate(R.id.fragment_item_detail, bundle)
            } else {
                itemView.findNavController().navigate(R.id.show_item_detail, bundle)
            }
        }

        val onContextClickListener = View.OnContextClickListener { v ->
            val item = v.tag as PlaceholderItem
            Toast.makeText(
                v.context,
                "Context click of item " + item.code,
                Toast.LENGTH_LONG
            ).show()
            true
        }
        setupRecyclerView(recyclerView, onClickListener, onContextClickListener)
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        onClickListener: View.OnClickListener,
        onContextClickListener: View.OnContextClickListener
    ) {
        adapter.onClickListener = onClickListener
        adapter.onContextClickListener = onContextClickListener

        recyclerView.adapter = adapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}