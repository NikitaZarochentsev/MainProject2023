package com.example.mainproject.domain.usecases

import com.example.mainproject.data.CowboysRepository
import com.example.mainproject.data.CowboysSharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateOrderUseCase(
    private val cowboysRepository: CowboysRepository,
    private val cowboysSharedPreferences: CowboysSharedPreferences,
) {
    class AccessTokenInvalidException : Exception()

    suspend operator fun invoke(
        productId: String,
        quantity: Int,
        size: String,
        house: String,
        apartment: String,
        etd: String
    ): Result<Unit> {
        var resultUseCase: Result<Unit> = Result.failure(AccessTokenInvalidException())
        val token = cowboysSharedPreferences.getToken() ?: return resultUseCase
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            cowboysRepository.createOrder(token, productId, quantity, size, house, apartment, etd)
        }
            .onSuccess {
                resultUseCase = Result.success(Unit)
            }
            .onFailure {
                resultUseCase = Result.failure(it)
            }
        return resultUseCase
    }
}