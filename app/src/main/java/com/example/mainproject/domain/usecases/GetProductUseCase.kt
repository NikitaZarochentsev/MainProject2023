package com.example.mainproject.domain.usecases

import com.example.mainproject.data.CowboysRepository
import com.example.mainproject.data.CowboysSharedPreferences
import com.example.mainproject.domain.models.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetProductUseCase(
    private val cowboysRepository: CowboysRepository,
    private val cowboysSharedPreferences: CowboysSharedPreferences,
) {

    class AccessTokenInvalidException : Exception()

    suspend operator fun invoke(productId: String): Result<Product> {
        var resultUseCase: Result<Product> = Result.failure(AccessTokenInvalidException())
        val token = cowboysSharedPreferences.getToken() ?: return resultUseCase
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            cowboysRepository.getProduct(token, productId)
        }
            .onSuccess {
                resultUseCase = Result.success(it.toProductDomain())
            }
            .onFailure {
                resultUseCase = Result.failure(it)
            }
        return resultUseCase
    }
}