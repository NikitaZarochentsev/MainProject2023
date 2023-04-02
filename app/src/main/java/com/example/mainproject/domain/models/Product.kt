package com.example.mainproject.domain.models

data class Product(
    val productId: String,
    val name: String,
    val description: String,
    val cost: Int,
    val iconURL: String
)