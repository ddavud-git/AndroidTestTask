package com.example.androidtesttask.adapters

import android.content.ClipData
import android.content.ClipDescription
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtesttask.databinding.ItemListContentBinding
import com.example.androidtesttask.placeholder.PlaceholderContent

class SimpleItemRecyclerViewAdapter(
    private val values: List<PlaceholderContent.PlaceholderItem>,
    private val onClickListener: View.OnClickListener,
    private val onContextClickListener: View.OnContextClickListener
) :
    RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            ItemListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id
        holder.contentView.text = item.content

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setOnContextClickListener(onContextClickListener)
            }

            setOnLongClickListener { v ->
                val clipItem = ClipData.Item(item.id)
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

    override fun getItemCount() = values.size

    inner class ViewHolder(binding: ItemListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.idText
        val contentView: TextView = binding.content
    }

}