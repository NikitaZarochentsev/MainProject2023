package com.example.mainproject.presentation.ui.orders

sealed class OrdersItemUiState {
    object Loading : OrdersItemUiState()
    data class Error(val throwable: Throwable) : OrdersItemUiState()
    object ListDisplay : OrdersItemUiState()
    object FullListDisplay : OrdersItemUiState()
}