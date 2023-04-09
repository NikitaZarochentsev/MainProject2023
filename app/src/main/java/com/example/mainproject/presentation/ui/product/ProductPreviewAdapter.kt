package com.example.mainproject.presentation.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.R

class ProductPreviewAdapter : RecyclerView.Adapter<ProductPreviewViewHolder>() {

    private var imageUrlList = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductPreviewViewHolder {
        return ProductPreviewViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_preview_product, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return imageUrlList.size
    }

    override fun onBindViewHolder(holder: ProductPreviewViewHolder, position: Int) {
        holder.bind(imageUrlList[position])
    }

    fun setImageUrls(imageUrlsList: List<String>) {
        val imageUrlDiffCallback = ImageUrlDiffCallback(this.imageUrlList, imageUrlsList)
        val diffResult = DiffUtil.calculateDiff(imageUrlDiffCallback)
        this.imageUrlList = imageUrlsList
        diffResult.dispatchUpdatesTo(this)
    }
}