package com.example.mainproject.domain

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mainproject.data.MockRepository
import com.example.mainproject.data.models.ProductListUiState
import kotlinx.coroutines.*

class CatalogViewModel : ViewModel() {

    val productListUiState = MutableLiveData<Result<ProductListUiState>>()

    private val mockRepository = MockRepository()

    private val handlerException = CoroutineExceptionHandler { _, throwable ->
        productListUiState.value = Result.failure(throwable)
    }

    fun updateProductListUiState() {
        viewModelScope.launch(handlerException) {
            val productsResult = async {
                mockRepository.getProducts()
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