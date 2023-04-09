package com.example.mainproject.presentation.ui.product

import androidx.recyclerview.widget.DiffUtil

class ImageUrlDiffCallback(private val oldList: List<String>, private val newList: List<String>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldImageUrl = oldList[oldItemPosition]
        val newImageUrl = newList[newItemPosition]
        return oldImageUrl == newImageUrl
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldImageUrl = oldList[oldItemPosition]
        val newImageUrl = newList[newItemPosition]
        return oldImageUrl == newImageUrl
    }
}