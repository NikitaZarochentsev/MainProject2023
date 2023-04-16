package com.example.mainproject.presentation.ui.product

import androidx.recyclerview.widget.DiffUtil
import com.example.mainproject.domain.models.Size

class SizeDiffCallback(
    private val oldList: List<Size>,
    private val newList: List<Size>
) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].value == newList[newItemPosition].value
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}