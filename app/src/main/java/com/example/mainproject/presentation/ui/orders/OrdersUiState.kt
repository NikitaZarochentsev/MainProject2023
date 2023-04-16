package com.example.mainproject.presentation.ui.orders

sealed class OrdersUiState {
    object Loading : OrdersUiState()
    object Empty : OrdersUiState()
    data class Error(val throwable: Throwable) : OrdersUiState()
    object Default : OrdersUiState()
}