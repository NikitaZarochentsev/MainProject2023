package com.example.mainproject.presentation.ui.orders.orderslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.R
import com.example.mainproject.domain.models.Order

class OrdersListAdapter : RecyclerView.Adapter<OrderViewHolder>() {

    private var orders = listOf<Order>()

    private lateinit var onItemExtraClickListener: OnItemExtraClickListener

    fun interface OnItemExtraClickListener {
        fun onItemClicked(orderId: String)
    }

    fun setOnItemClickListener(listener: OnItemExtraClickListener) {
        onItemExtraClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val viewHolder = OrderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_holder_order,
                parent,
                false
            )
        )
        viewHolder.buttonExtra.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition
            onItemExtraClickListener.onItemClicked(orders[position].id)
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
        val ordersListDiffCallback = OrdersListDiffCallback(this.orders, orders)
        val diffResult = DiffUtil.calculateDiff(ordersListDiffCallback)
        this.orders = orders.map(::Order).toList()
        diffResult.dispatchUpdatesTo(this)
    }
}