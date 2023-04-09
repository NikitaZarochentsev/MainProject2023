package com.example.mainproject.presentation.ui.product

import com.example.mainproject.domain.models.Product

sealed class ProductUiState {
    object Loading : ProductUiState()
    data class Error(val throwable: Throwable) : ProductUiState()
    data class Default(val product: Product) : ProductUiState()
}
