package com.example.mainproject.data.models

import com.example.mainproject.domain.models.Badge
import com.example.mainproject.domain.models.Product
import com.example.mainproject.domain.models.Size

data class GetProductsResponse(
    val data: List<Product>,
) {
    data class Product(
        val id: String,
        val title: String,
        val department: String,
        val price: Int,
        val badge: List<Badge>,
        val preview: String,
        val images: List<String>,
        val sizes: List<Size>,
        val description: String,
        val details: List<String>,
    ) {
        data class Badge(
            val value: String,
            val color: String,
        )

        data class Size(
            val value: String,
            val isAvailable: Boolean,
        )
    }
}