package com.example.mainproject.presentation.ui.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mainproject.domain.usecases.GetProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val getProductUseCase: GetProductUseCase) :
    ViewModel() {

    val productUiState = MutableLiveData<ProductUiState>()

    fun getProduct(productId: String) {
        productUiState.value = ProductUiState.Loading
        viewModelScope.launch {
            val productResult = async {
                getProductUseCase.invoke(productId)
            }

            productResult.await()
                .onSuccess { product ->
                    withContext(Dispatchers.Main) {
                        productUiState.value = ProductUiState.Default(product)
                    }
                }
                .onFailure { throwable ->
                    withContext(Dispatchers.Main) {
                        productUiState.value = ProductUiState.Error(throwable)
                    }
                }
        }
    }
}