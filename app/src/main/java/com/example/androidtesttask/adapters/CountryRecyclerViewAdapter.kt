package com.example.androidtesttask.adapters

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtesttask.Constants.LOG_IO_DATA_TAG
import com.example.androidtesttask.databinding.ItemListContentBinding
import com.example.androidtesttask.entity.PlaceholderItem

class CountryRecyclerViewAdapter :
    ListAdapter<PlaceholderItem, CountryRecyclerViewAdapter.ViewHolder>(
        DiffCallBack()
    ) {
    var onClickListener: View.OnClickListener? = null
    var onContextClickListener: View.OnContextClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            ItemListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.titleTextView.text = item.name
        holder.contentView.text = item.continent
        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setOnContextClickListener(onContextClickListener)
            }
        }
    }


    fun updateList(data: List<PlaceholderItem>) {
        Log.d(LOG_IO_DATA_TAG, "data loading to adapter $data")
        submitList(data)
    }


    inner class ViewHolder(binding: ItemListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val titleTextView: TextView = binding.titleText
        val contentView: TextView = binding.contentText
    }

}
