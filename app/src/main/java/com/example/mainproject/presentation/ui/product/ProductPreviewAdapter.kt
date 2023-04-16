package com.example.mainproject.presentation.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.R

class ProductPreviewAdapter() :
    RecyclerView.Adapter<ProductPreviewViewHolder>() {

    private var imageUrlList = mutableListOf<Pair<String, Boolean>>()
    private var currentImagePosition = 0

    private lateinit var onItemClickListener: OnItemClickListener

    fun interface OnItemClickListener {
        fun onItemClicked(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductPreviewViewHolder {
        val productPreviewViewHolder = ProductPreviewViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_preview_product, parent, false)
        )
        productPreviewViewHolder.itemView.setOnClickListener {
            val position = productPreviewViewHolder.adapterPosition
            onItemClickListener.onItemClicked(position)
        }

        return productPreviewViewHolder
    }

    override fun getItemCount(): Int {
        return imageUrlList.size
    }

    override fun onBindViewHolder(holder: ProductPreviewViewHolder, position: Int) {
        holder.bind(imageUrlList[position].first, imageUrlList[position].second)
    }

    fun setImageUrls(imageUrlsList: List<String>) {
        val pairList = mutableListOf<Pair<String, Boolean>>()
        for (position in imageUrlsList.indices) {
            pairList.add(Pair(imageUrlsList[position], position == currentImagePosition))
        }
        val imageUrlDiffCallback = ImageUrlDiffCallback(imageUrlList, pairList)
        val diffResult = DiffUtil.calculateDiff(imageUrlDiffCallback)
        imageUrlList = pairList
        diffResult.dispatchUpdatesTo(this)
    }

    fun setCurrentItemPosition(position: Int) {
        val newList = mutableListOf<Pair<String, Boolean>>().apply { addAll(imageUrlList) }
        newList[currentImagePosition] = Pair(newList[currentImagePosition].first, false)
        currentImagePosition = position
        newList[currentImagePosition] = Pair(newList[currentImagePosition].first, true)
        val imageUrlDiffCallback = ImageUrlDiffCallback(imageUrlList, newList)
        imageUrlList = newList
        val diffResult = DiffUtil.calculateDiff(imageUrlDiffCallback)
        diffResult.dispatchUpdatesTo(this)
    }
}