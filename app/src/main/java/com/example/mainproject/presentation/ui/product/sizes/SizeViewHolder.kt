package com.example.mainproject.presentation.ui.product.sizes

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.databinding.ViewHolderSizeBinding

class SizeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ViewHolderSizeBinding.bind(itemView)

    fun bind(size: String) = binding.apply {
        buttonItemSize.text = size
    }
}