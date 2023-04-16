package com.example.mainproject.presentation.ui.catalog

import com.example.mainproject.domain.models.Product

sealed class CatalogUiState {
    object Loading : CatalogUiState()
    object Empty : CatalogUiState()
    data class Error(val throwable: Throwable) : CatalogUiState()
    data class Default(val products: List<Product>) : CatalogUiState()
}
