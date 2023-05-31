package com.example.mainproject.presentation.ui.product.sizes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.R
import com.example.mainproject.domain.models.Size

class SizesAdapter : RecyclerView.Adapter<SizeViewHolder>() {

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
                .inflate(R.layout.view_holder_size, parent, false)
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition
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
        this.sizes = sizes.map(::Size).toList()
        for (index in 0..this.sizes.size) {
            notifyItemInserted(index)
        }
    }
}