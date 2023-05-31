package com.example.mainproject.presentation.ui.orders.orderslist

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.mainproject.R
import com.example.mainproject.databinding.ViewHolderOrderBinding
import com.example.mainproject.domain.models.Order
import com.example.mainproject.domain.models.OrderStatus
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ViewHolderOrderBinding.bind(itemView)
    val buttonExtra = binding.buttonExtraItemOrders

    fun bind(order: Order) = binding.apply {
        imageViewItemOrders.load(order.productPreview) {
            transformations(RoundedCornersTransformation(itemView.resources.getDimension(R.dimen.normal_border_radius_100)))
        }
        textViewHeaderItemOrders.text =
            itemView.resources.getString(
                R.string.item_orders_header,
                order.number,
                OffsetDateTime.parse(order.createdAt, DateTimeFormatter.ISO_DATE_TIME)
                    .format(DateTimeFormatter.ofPattern(itemView.resources.getString(R.string.date_order_header_format)))
            )
        when (order.status) {
            OrderStatus.in_work -> {
                textViewEnabledItemOrders.text =
                    itemView.resources.getString(R.string.item_orders_in_work)
                textViewEnabledItemOrders.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.green_87pc
                    )
                )
                textViewInformationItemOrders.text = itemView.resources.getString(
                    R.string.item_orders_delivery_information,
                    OffsetDateTime.parse(order.etd, DateTimeFormatter.ISO_DATE_TIME)
                        .format(DateTimeFormatter.ofPattern(itemView.resources.getString(R.string.date_order_header_format))),
                    order.deliveryAddress
                )
                buttonExtraItemOrders.visibility = View.VISIBLE
            }
            OrderStatus.cancelled -> {
                textViewEnabledItemOrders.text =
                    itemView.resources.getString(R.string.item_orders_canceled)
                textViewEnabledItemOrders.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.red_87pc
                    )
                )
                textViewInformationItemOrders.text = itemView.resources.getString(
                    R.string.item_orders_cancellation_information,
                    OffsetDateTime.parse(order.etd, DateTimeFormatter.ISO_DATE_TIME)
                        .format(DateTimeFormatter.ofPattern(itemView.resources.getString(R.string.date_order_header_format)))
                )
                buttonExtraItemOrders.visibility = View.INVISIBLE
            }
            OrderStatus.done -> {
                textViewEnabledItemOrders.text =
                    itemView.resources.getString(R.string.item_orders_done)
                textViewEnabledItemOrders.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.blue_87pc
                    )
                )
                textViewInformationItemOrders.text = itemView.resources.getString(
                    R.string.item_orders_done_information,
                    OffsetDateTime.parse(order.etd, DateTimeFormatter.ISO_DATE_TIME)
                        .format(DateTimeFormatter.ofPattern(itemView.resources.getString(R.string.date_order_header_format)))
                )
                buttonExtraItemOrders.visibility = View.INVISIBLE
            }
        }
        textViewTitleItemOrders.text = itemView.resources.getString(
            R.string.item_orders_title,
            order.productQuantity,
            order.productSize,
            order.productTitle
        )
    }
}