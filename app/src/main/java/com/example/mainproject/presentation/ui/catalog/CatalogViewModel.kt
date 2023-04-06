package com.example.mainproject.presentation.ui.catalog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mainproject.domain.usecases.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(private val getProductsUseCase: GetProductsUseCase) :
    ViewModel() {

    val catalogUiState = MutableLiveData<CatalogUiState>()

    private val handlerException = CoroutineExceptionHandler { _, throwable ->
        catalogUiState.value = CatalogUiState.Error(throwable)
    }

    fun updateProductList() {
        catalogUiState.value = CatalogUiState.Loading
        viewModelScope.launch(handlerException) {
            val productsResult = async {
                getProductsUseCase.invoke()
            }

            productsResult.await()
                .onSuccess { productList ->
                    withContext(Dispatchers.Main) {
                        if (productList.isEmpty()) {
                            catalogUiState.value = CatalogUiState.Empty
                        } else {
                            catalogUiState.value = CatalogUiState.Default(productList)
                        }
                    }
                }
                .onFailure {
                    catalogUiState.value = CatalogUiState.Error(it)
                }
        }
    }
}