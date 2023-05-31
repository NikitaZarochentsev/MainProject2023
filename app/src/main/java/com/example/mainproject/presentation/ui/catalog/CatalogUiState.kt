package com.example.mainproject.presentation.ui.catalog

sealed class CatalogUiState {
    object Loading : CatalogUiState()
    data class Error(val throwable: Throwable) : CatalogUiState()
    object ListDisplay : CatalogUiState()
    object FullListDisplay : CatalogUiState()
}
