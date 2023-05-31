package com.example.mainproject.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateOrderBody(
    val productId: String,
    val quantity: Int,
    val size: String,
    val house: String,
    val apartment: String,
    val etd: String,
)
