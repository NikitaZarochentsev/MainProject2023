package com.example.mainproject.domain.models

data class Product(
    val id: String,
    val title: String,
    val department: String,
    val price: Int,
    val badge: Badge,
    val preview: String,
    val images: List<String>,
    val sizes: List<Size>,
    val description: String,
    val details: List<String>
)
