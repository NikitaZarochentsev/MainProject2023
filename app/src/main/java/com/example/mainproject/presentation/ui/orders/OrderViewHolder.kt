package com.example.mainproject.presentation.ui.orders

import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.mainproject.R
import com.example.mainproject.databinding.ViewHolderItemOrdersBinding
import com.example.mainproject.domain.models.Order
import com.example.mainproject.domain.models.OrderStatus
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ViewHolderItemOrdersBinding.bind(itemView)

    fun bind(order: Order) {
        binding.imageViewItemOrders.load(order.productPreview) {
            transformations(RoundedCornersTransformation(itemView.resources.getDimension(R.dimen.image_view_holder_item_orders_border_radius)))
        }

        binding.textViewHeaderItemOrders.text =
            itemView.resources.getString(
                R.string.item_orders_header,
                order.number,
                OffsetDateTime.parse(order.createdAt, DateTimeFormatter.ISO_DATE_TIME)
                    .format(DateTimeFormatter.ofPattern(itemView.resources.getString(R.string.date_order_header_format)))
            )

        when (order.status) {
            OrderStatus.in_work -> {
                binding.textViewEnabledItemOrders.text =
                    itemView.resources.getString(R.string.item_orders_in_work)
                binding.textViewEnabledItemOrders.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.green_87pc
                    )
                )
                binding.textViewInformationItemOrders.text = itemView.resources.getString(
                    R.string.item_orders_delivery_information,
                    OffsetDateTime.parse(order.etd, DateTimeFormatter.ISO_DATE_TIME)
                        .format(DateTimeFormatter.ofPattern(itemView.resources.getString(R.string.date_order_header_format))),
                    order.deliveryAddress
                )
            }
            OrderStatus.cancelled -> {
                binding.textViewEnabledItemOrders.text =
                    itemView.resources.getString(R.string.item_orders_canceled)
                binding.textViewEnabledItemOrders.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.red_87pc
                    )
                )
                binding.textViewInformationItemOrders.text = itemView.resources.getString(
                    R.string.item_orders_cancellation_information,
                    OffsetDateTime.parse(order.etd, DateTimeFormatter.ISO_DATE_TIME)
                        .format(DateTimeFormatter.ofPattern(itemView.resources.getString(R.string.date_order_header_format)))
                )
            }
            OrderStatus.done -> {
                binding.textViewEnabledItemOrders.text =
                    itemView.resources.getString(R.string.item_orders_done)
                binding.textViewEnabledItemOrders.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.blue_87pc
                    )
                )
                binding.textViewInformationItemOrders.text = itemView.resources.getString(
                    R.string.item_orders_done_information,
                    OffsetDateTime.parse(order.etd, DateTimeFormatter.ISO_DATE_TIME)
                        .format(DateTimeFormatter.ofPattern(itemView.resources.getString(R.string.date_order_header_format)))
                )
            }
        }

        binding.textViewTitleItemOrders.text = itemView.resources.getString(
            R.string.item_orders_title,
            order.productQuantity,
            order.productSize,
            order.productTitle
        )

        binding.buttonExtraItemOrders.setOnClickListener {
            Toast.makeText(itemView.context, R.string.item_orders_cancel, Toast.LENGTH_SHORT).show()
        }
    }
}