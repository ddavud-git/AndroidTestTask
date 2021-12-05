package com.example.androidtesttask.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.androidtesttask.entity.PlaceholderItem

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
