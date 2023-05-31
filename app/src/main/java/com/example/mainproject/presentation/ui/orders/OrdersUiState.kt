package com.example.mainproject.presentation.ui.orders

sealed class OrdersUiState {
    object Loading : OrdersUiState()
    data class Error(val throwable: Throwable) : OrdersUiState()
    data class ListDisplay(val activeIsEmpty: Boolean) : OrdersUiState()
    data class FullListDisplay(val activeIsEmpty: Boolean) : OrdersUiState()
    object Empty : OrdersUiState()
}