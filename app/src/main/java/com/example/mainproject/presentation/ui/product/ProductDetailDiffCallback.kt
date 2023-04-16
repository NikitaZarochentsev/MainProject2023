package com.example.mainproject.presentation.ui.product

import androidx.recyclerview.widget.DiffUtil

class ProductDetailDiffCallback(
    private val oldList: List<String>,
    private val newList: List<String>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldDetail = oldList[oldItemPosition]
        val newDetail = newList[newItemPosition]
        return oldDetail == newDetail
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldDetail = oldList[oldItemPosition]
        val newDetail = newList[newItemPosition]
        return oldDetail == newDetail
    }
}