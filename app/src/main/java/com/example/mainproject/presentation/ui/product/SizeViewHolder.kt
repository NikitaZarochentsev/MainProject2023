package com.example.mainproject.presentation.ui.product

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.databinding.ViewHolderItemSizeBottomBinding

class SizeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ViewHolderItemSizeBottomBinding.bind(itemView)

    fun bind(size: String){
        binding.buttonItemSize.text = size
    }
}