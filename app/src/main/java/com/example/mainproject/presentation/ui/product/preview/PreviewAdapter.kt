package com.example.mainproject.presentation.ui.product.preview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.R

class PreviewAdapter : RecyclerView.Adapter<PreviewViewHolder>() {

    private var imageUrlList = mutableListOf<Pair<String, Boolean>>()
    private var currentImagePosition = 0

    private lateinit var onItemClickListener: OnItemClickListener

    fun interface OnItemClickListener {
        fun onItemClicked(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewViewHolder {
        val previewViewHolder = PreviewViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_preview_product, parent, false)
        )
        previewViewHolder.itemView.setOnClickListener {
            val position = previewViewHolder.bindingAdapterPosition
            onItemClickListener.onItemClicked(position)
        }

        return previewViewHolder
    }

    override fun getItemCount(): Int {
        return imageUrlList.size
    }

    override fun onBindViewHolder(holder: PreviewViewHolder, position: Int) {
        holder.bind(imageUrlList[position].first, imageUrlList[position].second)
    }

    fun setImageUrls(imageUrlsList: List<String>) {
        val pairList = mutableListOf<Pair<String, Boolean>>()
        for (position in imageUrlsList.indices) {
            pairList.add(Pair(imageUrlsList[position], position == currentImagePosition))
        }
        imageUrlList = pairList
        for (index in 0..imageUrlsList.size) {
            notifyItemInserted(index)
        }
    }

    fun setCurrentItemPosition(position: Int) {
        imageUrlList[currentImagePosition] = imageUrlList[currentImagePosition].first to false
        notifyItemChanged(currentImagePosition)
        currentImagePosition = position
        imageUrlList[currentImagePosition] = imageUrlList[currentImagePosition].first to true
        notifyItemChanged(currentImagePosition)
    }
}