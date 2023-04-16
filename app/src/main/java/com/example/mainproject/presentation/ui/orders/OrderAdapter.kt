package com.example.mainproject.presentation.ui.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.R
import com.example.mainproject.domain.models.Order

class OrderAdapter : RecyclerView.Adapter<OrderViewHolder>() {

    private var orders = listOf<Order>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_holder_item_orders,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    fun setOrders(orders: List<Order>) {
        val ordersDiffCallback = OrdersDiffCallback(this.orders, orders)
        val diffResult = DiffUtil.calculateDiff(ordersDiffCallback)
        this.orders = orders
        diffResult.dispatchUpdatesTo(this)
    }
}