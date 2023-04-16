package com.example.mainproject.presentation.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.R

class ProductDetailAdapter : RecyclerView.Adapter<ProductDetailViewHolder>() {

    private var details = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductDetailViewHolder {
        return ProductDetailViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_detail_product, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return details.size
    }

    override fun onBindViewHolder(holder: ProductDetailViewHolder, position: Int) {
        holder.bind(details[position])
    }

    fun setDetails(details: List<String>) {
        val productDetailDiffCallback = ProductDetailDiffCallback(this.details, details)
        this.details = details
        val diffResult = DiffUtil.calculateDiff(productDetailDiffCallback)
        diffResult.dispatchUpdatesTo(this)
    }
}