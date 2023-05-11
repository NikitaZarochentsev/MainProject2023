package com.example.mainproject.domain.repositories

import com.example.mainproject.domain.models.Order
import com.example.mainproject.domain.models.Product
import com.example.mainproject.domain.models.Profile

interface MockRepository {
    suspend fun signIn(login: String, password: String): Result<Profile>

    suspend fun getProducts(pageNumber: Int, pageSize: Int): Result<List<Product>>

    suspend fun getProfile(): Result<Profile>

    fun signOut()

    suspend fun getProduct(productId: String): Result<Product>

    suspend fun getOrders(pagerNumber: Int, pageSize: Int): Result<List<Order>>

    suspend fun cancelOrder(orderId: String): Result<Order>
}