package com.example.mainproject.presentation.ui.orders.orderslist

import androidx.recyclerview.widget.DiffUtil
import com.example.mainproject.domain.models.Order

class OrdersListDiffCallback(
    private val oldList: List<Order>,
    private val newList: List<Order>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldOrder = oldList[oldItemPosition]
        val newOrder = newList[newItemPosition]
        return (oldOrder.status == newOrder.status) && (oldOrder.id == newOrder.id)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldOrder = oldList[oldItemPosition]
        val newOrder = newList[newItemPosition]
        return oldOrder == newOrder
    }
}