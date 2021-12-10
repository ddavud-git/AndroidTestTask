package com.example.androidtesttask.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtesttask.Constants
import com.example.androidtesttask.R
import com.example.androidtesttask.adapters.CountryRecyclerViewAdapter
import com.example.androidtesttask.databinding.FragmentItemListBinding
import com.example.androidtesttask.di.viewmodel.AppViewModelFactory
import com.example.androidtesttask.entity.PlaceholderItem
import com.example.androidtesttask.network.ResultStatus
import com.example.androidtesttask.ui.viewmodel.CountriesViewModel
import com.example.androidtesttask.util.DataConverter.convertDataToPlaceHolder
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CountryListFragment : DaggerFragment() {

    private var _binding: FragmentItemListBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: CountryRecyclerViewAdapter

    @Inject
    lateinit var providerFactory: AppViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), providerFactory)[CountriesViewModel::class.java]
    }

    init {
        lifecycleScope.launchWhenStarted { //This code block is triggered only when lifecycle state is STARTED
                if (lifecycle.currentState >= Lifecycle.State.STARTED) { // this block providing user interact after lifecycle state being started
                    viewModel.cache.observe(viewLifecycleOwner, { data ->
                        Log.d(Constants.LOG_IO_DATA_TAG, "data on local database ${data}")
                        if (data.isEmpty())
                            viewModel.getCountryList()
                        else
                            adapter.updateList(data = data.convertDataToPlaceHolder())
                    })

                    viewModel.networkResLiveData.observe(viewLifecycleOwner, {
                        when (it) {
                            is ResultStatus.Success -> logNetworkMsg("Request done successfully")
                            is ResultStatus.ErrorRes -> showErrorMessage(it.e)
                            is ResultStatus.SaveCacheFail -> showToastMsg("Save to cache not success error ${it.e.printStackTrace()}")
                            is ResultStatus.NetworkError -> showToastMsg("Check your connectivity")
                            else -> logNetworkMsg("Result is not appointed status is still IDLE")
                        }
                    })
                }
        }
    }

    private fun showErrorMessage(e: Exception) {
       showToastMsg("Error while sending request ${e.printStackTrace()}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = CountryRecyclerViewAdapter()
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
            showToastMsg("Context click of item " + item.code)
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

    private fun logNetworkMsg(msg: String) {
        Log.d(Constants.LOG_NETWORK_TAG, msg)
    }

    private fun showToastMsg(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }
}