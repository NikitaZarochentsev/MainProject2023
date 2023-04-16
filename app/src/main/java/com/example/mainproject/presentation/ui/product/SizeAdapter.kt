package com.example.mainproject.presentation.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.R
import com.example.mainproject.domain.models.Size

class SizeAdapter : RecyclerView.Adapter<SizeViewHolder>() {

    private var sizes = listOf<Size>()

    private lateinit var onItemClickListener: OnItemClickListener

    fun interface OnItemClickListener {
        fun onItemClicked(size: String)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeViewHolder {
        val viewHolder = SizeViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_item_size_bottom, parent, false)
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClickListener.onItemClicked(sizes[position].value)
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return sizes.size
    }

    override fun onBindViewHolder(holder: SizeViewHolder, position: Int) {
        holder.bind(sizes[position].value)
    }

    fun setSizes(sizes: List<Size>) {
        val sizeDiffCallback = SizeDiffCallback(this.sizes, sizes)
        val diffResult = DiffUtil.calculateDiff(sizeDiffCallback)
        this.sizes = sizes
        diffResult.dispatchUpdatesTo(this)
    }
}