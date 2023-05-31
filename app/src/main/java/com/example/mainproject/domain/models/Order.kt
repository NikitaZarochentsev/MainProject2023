package com.example.mainproject.domain.models

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
    var status: OrderStatus,
    var productTitle: String,
) {
    constructor(
        order: Order,
    ) : this(
        order.id,
        order.number,
        order.productId,
        order.productPreview,
        order.productQuantity,
        order.productSize,
        order.createdAt,
        order.etd,
        order.deliveryAddress,
        order.status,
        order.productTitle
    )
}