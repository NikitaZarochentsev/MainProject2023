package com.example.mainproject.presentation.ui.catalog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mainproject.domain.models.Product
import com.example.mainproject.domain.usecases.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(val getProductsUseCase: GetProductsUseCase) :
    ViewModel() {

    val productListUiState = MutableLiveData<Result<ProductListUiState>>()

    private val handlerException = CoroutineExceptionHandler { _, throwable ->
        productListUiState.value = Result.failure(throwable)
    }

    fun updateProductListUiState() {
        viewModelScope.launch(handlerException) {
            val productsResult = async {
                getProductsUseCase.invoke()
            }

            productsResult.await()
                .onSuccess {
                    withContext(Dispatchers.Main) {
                        productListUiState.value = Result.success(ProductListUiState(it))
                    }
                }
                .onFailure {
                    throw it
                }
        }
    }
}

data class ProductListUiState(
    var productList: List<Product>
)