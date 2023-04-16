package com.example.mainproject.presentation.ui.orders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor() :
    ViewModel() {

    val ordersUiState = MutableLiveData<OrdersUiState>()

    init {
        ordersUiState.value = OrdersUiState.Default
    }
}