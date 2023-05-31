package com.example.mainproject.domain.usecases

import com.example.mainproject.data.CowboysRepository
import com.example.mainproject.data.CowboysSharedPreferences
import com.example.mainproject.domain.models.Badge
import com.example.mainproject.domain.models.Product
import com.example.mainproject.domain.models.Size
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetProductsUseCase(
    private val cowboysRepository: CowboysRepository,
    private val cowboysSharedPreferences: CowboysSharedPreferences,
) {

    companion object {
        private const val PAGE_SIZE = 6
    }

    class AccessTokenInvalidException : Exception()

    suspend operator fun invoke(pageNumber: Int): Result<List<Product>> {
        var resultUseCase: Result<List<Product>> = Result.failure(AccessTokenInvalidException())
        val token = cowboysSharedPreferences.getToken() ?: return resultUseCase
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            cowboysRepository.getProducts(token, pageNumber, PAGE_SIZE)
        }
            .onSuccess { productsResult ->
                val products = mutableListOf<Product>()
                for (product in productsResult) {
                    val sizes = mutableListOf<Size>()
                    for (size in product.sizes) {
                        sizes.add(
                            Size(
                                value = size.value,
                                isAvailable = size.isAvailable
                            )
                        )
                    }
                    products.add(
                        Product(
                            id = product.id,
                            title = product.title,
                            department = product.department,
                            price = product.price,
                            badge = Badge(
                                value = product.badge[0].value,
                                color = product.badge[0].color
                            ),
                            preview = product.preview,
                            images = product.images,
                            sizes = sizes,
                            description = product.description,
                            details = product.details
                        )
                    )
                }
                resultUseCase = Result.success(products)
            }
            .onFailure {
                resultUseCase = Result.failure(it)
            }
        return resultUseCase
    }
}