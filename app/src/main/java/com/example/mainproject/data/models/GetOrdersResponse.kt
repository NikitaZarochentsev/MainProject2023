package com.example.mainproject.data.models

import com.example.mainproject.domain.models.Order
import com.example.mainproject.domain.models.OrderStatus

data class GetOrdersResponse(
    val data: List<Order>,
) {
    data class Order(
        var id: String,
        var number: Int,
        var productId: String,
        var productPreview: String,
        var productQuantity: Int,
        var productSize: String,
        var createdAt: String,
        var etd: String,
        var deliveryAddress: String,
        var status: String,
    ) {
        fun toOrderDomain(productTitle: String): com.example.mainproject.domain.models.Order {
            val statusDomain =
                when (status) {
                    "in_work" -> OrderStatus.in_work
                    "done" -> OrderStatus.done
                    "cancelled" -> OrderStatus.cancelled
                    else -> OrderStatus.in_work
                }
            return Order(
                id = id,
                number = number,
                productId = productId,
                productPreview = productPreview,
                productQuantity = productQuantity,
                productSize = productSize,
                createdAt = createdAt,
                etd = etd,
                deliveryAddress = deliveryAddress,
                status = statusDomain,
                productTitle = productTitle
            )
        }
    }
}
