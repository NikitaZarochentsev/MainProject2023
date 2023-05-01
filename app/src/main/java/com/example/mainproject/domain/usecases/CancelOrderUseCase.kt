package com.example.mainproject.domain.usecases

import com.example.mainproject.domain.models.Order
import com.example.mainproject.domain.repositories.MockRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class CancelOrderUseCase(private val mockRepository: MockRepository) {

    suspend operator fun invoke(orderId: String): Result<Order> {
        val order = CoroutineScope(Dispatchers.IO).async {
            val cancelOrderResult = async {
                mockRepository.cancelOrder(orderId)
            }
            cancelOrderResult.await()
                .onSuccess {
                    return@async Result.success(it)
                }
                .onFailure {
                    Result.failure<Throwable>(it)
                }
        }

        return order.await()
    }
}