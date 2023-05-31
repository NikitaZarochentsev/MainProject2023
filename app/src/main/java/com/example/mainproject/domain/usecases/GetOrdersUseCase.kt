package com.example.mainproject.domain.usecases

import com.example.mainproject.data.CowboysRepository
import com.example.mainproject.data.CowboysSharedPreferences
import com.example.mainproject.domain.models.Order
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetOrdersUseCase(
    private val cowboysRepository: CowboysRepository,
    private val cowboysSharedPreferences: CowboysSharedPreferences,
) {

    companion object {
        private const val PAGE_SIZE = 6
    }

    class AccessTokenInvalidException : Exception()
    class ProductIdInvalidException : Exception()

    suspend operator fun invoke(pageNumber: Int): Result<List<Order>> {
        var resultUseCase: Result<List<Order>> = Result.failure(AccessTokenInvalidException())
        val token = cowboysSharedPreferences.getToken() ?: return resultUseCase
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            cowboysRepository.getOrders(token, pageNumber, PAGE_SIZE)
        }
            .onSuccess { ordersResult ->
                val orders = mutableListOf<Order>()
                for (order in ordersResult) {
                    withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                        cowboysRepository.getProduct(token, order.productId)
                    }
                        .onSuccess { product ->
                            orders.add(order.toOrderDomain(product.title))
                        }
                        .onFailure {
                            throw ProductIdInvalidException()
                        }
                }
                resultUseCase = Result.success(orders)
            }
            .onFailure {
                resultUseCase = Result.failure(it)
            }
        return resultUseCase
    }
}