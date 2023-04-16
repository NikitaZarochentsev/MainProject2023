package com.example.mainproject.presentation.ui.orders

import com.example.mainproject.domain.models.Order

sealed class OrdersItemUiState {
    object Loading: OrdersItemUiState()
    object Empty: OrdersItemUiState()
    data class Error(val throwable: Throwable) : OrdersItemUiState()
    data class Default(val orders: List<Order>) : OrdersItemUiState()
}