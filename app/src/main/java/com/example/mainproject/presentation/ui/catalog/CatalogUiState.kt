package com.example.mainproject.presentation.ui.catalog

import com.example.mainproject.domain.models.Product

sealed class CatalogUiState {
    object Loading : CatalogUiState()
    data class Error(val throwable: Throwable) : CatalogUiState()
    object ListDisplay : CatalogUiState()
    object FullListDisplay : CatalogUiState()
}
