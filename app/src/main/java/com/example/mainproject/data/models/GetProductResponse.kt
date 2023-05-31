package com.example.mainproject.data.models

import com.example.mainproject.domain.models.Badge
import com.example.mainproject.domain.models.Product

data class GetProductResponse(
    val data: Product,
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
        ) {
            fun toSizeDomain(): com.example.mainproject.domain.models.Size {
                return com.example.mainproject.domain.models.Size(
                    value = value,
                    isAvailable = isAvailable
                )
            }
        }

        fun toProductDomain(): com.example.mainproject.domain.models.Product {
            val sizes = mutableListOf<com.example.mainproject.domain.models.Size>()
            for (size in this.sizes) {
                sizes.add(size.toSizeDomain())
            }
            return Product(
                id = id,
                title = title,
                department = department,
                price = price,
                badge = com.example.mainproject.domain.models.Badge(
                    value = badge[0].value,
                    color = badge[0].color
                ),
                preview = preview,
                images = images,
                sizes = sizes,
                description = description,
                details = details
            )
        }
    }
}
