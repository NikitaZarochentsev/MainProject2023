package com.example.mainproject.domain.usecases

import com.example.mainproject.domain.models.Product
import com.example.mainproject.domain.repositories.MockRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class GetProductUseCase(private val mockRepository: MockRepository) {

    suspend operator fun invoke(productId: String): Result<Product> {
        val product = CoroutineScope(Dispatchers.IO).async {
            val productResult = async {
                mockRepository.getProduct(productId)
            }

            productResult.await()
                .onSuccess {
                    return@async Result.success(it)
                }
                .onFailure {
                    Result.failure<Throwable>(it)
                }
        }

        return product.await()
    }
}