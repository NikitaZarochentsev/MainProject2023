package com.example.mainproject.data.models

data class CancelOrderResponse(
    val data: Order,
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
    )
}
