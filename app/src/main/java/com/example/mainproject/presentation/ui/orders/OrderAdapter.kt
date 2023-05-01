package com.example.mainproject.presentation.ui.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.R
import com.example.mainproject.domain.models.Order
import com.example.mainproject.domain.models.OrderStatus

class OrderAdapter() : RecyclerView.Adapter<OrderViewHolder>() {

    private var orders = listOf<Order>()

    private lateinit var onItemExtraClickListener: OnItemExtraClickListener

    private lateinit var type: String

    fun interface OnItemExtraClickListener {
        fun onItemClicked(orderId: String)
    }

    fun setOnItemClickListener(listener: OnItemExtraClickListener) {
        onItemExtraClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val viewHolder = OrderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_holder_item_orders,
                parent,
                false
            )
        )
        viewHolder.buttonExtra.setOnClickListener {
            val position = viewHolder.adapterPosition
            val newOrders = orders.map(::Order).toMutableList()
            if (type == OrdersItemViewPager2Fragment.ALL_TYPE) {
                newOrders[position].status = OrderStatus.cancelled
            } else {
                newOrders.removeAt(position)
            }
            onItemExtraClickListener.onItemClicked(orders[position].id)
            setOrders(newOrders)
        }
        return viewHolder
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

    fun setTypeOrders(type: String) {
        this.type = type
    }
}