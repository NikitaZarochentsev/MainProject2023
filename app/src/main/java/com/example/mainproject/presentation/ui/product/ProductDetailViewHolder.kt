package com.example.mainproject.presentation.ui.product

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.R
import com.example.mainproject.databinding.ViewHolderDetailProductBinding

class ProductDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ViewHolderDetailProductBinding.bind(itemView)

    fun bind(detail: String) {
        binding.textViewDetailProduct.text = detail
    }
}