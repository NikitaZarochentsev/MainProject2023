package com.example.mainproject.presentation.ui.product

import androidx.recyclerview.widget.DiffUtil

class ImageUrlDiffCallback(
    private val oldList: List<Pair<String, Boolean>>,
    private val newList: List<Pair<String, Boolean>>
) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldImageUrl = oldList[oldItemPosition].second
        val newImageUrl = newList[newItemPosition].second
        return oldImageUrl == newImageUrl
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldImageUrl = oldList[oldItemPosition].first
        val newImageUrl = newList[newItemPosition].first
        return oldImageUrl == newImageUrl
    }
}