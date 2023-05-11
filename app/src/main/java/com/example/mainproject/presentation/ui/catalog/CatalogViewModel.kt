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
class CatalogViewModel @Inject constructor(private val getProductsUseCase: GetProductsUseCase) :
    ViewModel() {

    val catalogUiState = MutableLiveData<CatalogUiState>()
    val products = MutableLiveData<List<Product>>()
    var isLoading = false

    private var nextPageNumber = 0

    private val handlerException = CoroutineExceptionHandler { _, throwable ->
        catalogUiState.value = CatalogUiState.Error(throwable)
    }

    fun updateProductList() {
        isLoading = true
        catalogUiState.value = CatalogUiState.Loading
        nextPageNumber = 0
        viewModelScope.launch(handlerException) {
            val productsResult = async {
                getProductsUseCase.invoke(0)
            }

            productsResult.await()
                .onSuccess { productList ->
                    withContext(Dispatchers.Main) {
                        products.value = productList
                        if (productList.isNotEmpty()) {
                            nextPageNumber = 1
                            catalogUiState.value = CatalogUiState.ListDisplay
                        }
                    }
                }
                .onFailure {
                    catalogUiState.value = CatalogUiState.Error(it)
                }

            isLoading = false
        }
    }

    fun loadProductPage() {
        isLoading = true
        viewModelScope.launch(handlerException) {
            val newProductsResult = async {
                getProductsUseCase.invoke(nextPageNumber)
            }

            newProductsResult.await()
                .onSuccess { newProducts ->
                    if (newProducts.isEmpty()) {
                        catalogUiState.value = CatalogUiState.FullListDisplay
                    } else {
                        nextPageNumber += 1
                        val oldProducts = products.value
                        val allProduct = mutableListOf<Product>()
                        if (oldProducts != null) {
                            allProduct.addAll(oldProducts)
                        }
                        allProduct.addAll(newProducts)
                        products.value = allProduct
                        catalogUiState.value = CatalogUiState.ListDisplay
                    }
                }
                .onFailure {
                    catalogUiState.value = CatalogUiState.Error(it)
                }

            isLoading = false
        }
    }
}