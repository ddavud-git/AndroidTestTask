package com.example.androidtesttask.adapters

import android.content.ClipData
import android.content.ClipDescription
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtesttask.Constants.LOG_IO_DATA_TAG
import com.example.androidtesttask.databinding.ItemListContentBinding
import com.example.androidtesttask.entity.PlaceholderItem

class SimpleItemRecyclerViewAdapter :
    ListAdapter<PlaceholderItem, SimpleItemRecyclerViewAdapter.ViewHolder>(
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
        holder.idView.text = item.code
        holder.contentView.text = item.name

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setOnContextClickListener(onContextClickListener)
            }

            setOnLongClickListener { v ->
                val clipItem = ClipData.Item(item.code)
                val dragData = ClipData(
                    v.tag as? CharSequence,
                    arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                    clipItem
                )

                if (Build.VERSION.SDK_INT >= 24) {
                    v.startDragAndDrop(
                        dragData,
                        View.DragShadowBuilder(v),
                        null,
                        0
                    )
                } else {
                    v.startDrag(
                        dragData,
                        View.DragShadowBuilder(v),
                        null,
                        0
                    )
                }
            }
        }
    }


    fun updateList(data: List<PlaceholderItem>) {
        Log.d(LOG_IO_DATA_TAG, "data loading to adapter ${data}")
        submitList(data)
    }


    inner class ViewHolder(binding: ItemListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.idText
        val contentView: TextView = binding.content
    }

}

class DiffCallBack : DiffUtil.ItemCallback<PlaceholderItem>() {
    override fun areItemsTheSame(
        oldItem: PlaceholderItem,
        newItem: PlaceholderItem
    ): Boolean =
        oldItem.code == newItem.code


    override fun areContentsTheSame(
        oldItem: PlaceholderItem,
        newItem: PlaceholderItem
    ): Boolean = oldItem.name == newItem.name

}
