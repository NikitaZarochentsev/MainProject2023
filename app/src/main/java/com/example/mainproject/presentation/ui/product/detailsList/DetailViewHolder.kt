package com.example.mainproject.presentation.ui.product.detailsList

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.databinding.ViewHolderDetailProductBinding

class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ViewHolderDetailProductBinding.bind(itemView)

    fun bind(detail: String) = binding.apply {
        textViewDetailProduct.text = detail
    }
}