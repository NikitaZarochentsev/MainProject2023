package com.example.mainproject.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(
    val id: String,
    val number: Int,
    val productId: String,
    val productPreview: String,
    val productQuantity: Int,
    val productSize: String,
    val createdAt: String,
    val etd: String,
    val deliveryAddress: String,
    val status: OrderStatus,
    val productTitle: String
) : Parcelable