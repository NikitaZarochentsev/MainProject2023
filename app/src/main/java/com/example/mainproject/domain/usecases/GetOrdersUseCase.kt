package com.example.mainproject.domain.usecases

import com.example.mainproject.domain.models.Order
import com.example.mainproject.domain.repositories.MockRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class GetOrdersUseCase(private val mockRepository: MockRepository) {

    suspend operator fun invoke(pageNumber: Int): Result<List<Order>> {
        val orders = CoroutineScope(Dispatchers.IO).async {
            val ordersResult = async {
                mockRepository.getOrders(pageNumber, 6)
            }

            ordersResult.await()
                .onSuccess {
                    return@async Result.success(it)
                }
                .onFailure {
                    Result.failure<Throwable>(it)
                }
        }

        return orders.await()
    }
}