package com.example.mainproject.presentation.ui.orders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mainproject.domain.models.Order
import com.example.mainproject.domain.models.OrderStatus
import com.example.mainproject.domain.usecases.CancelOrderUseCase
import com.example.mainproject.domain.usecases.GetOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class OrdersItemViewModel @Inject constructor(
    private val getOrdersUseCase: GetOrdersUseCase,
    private val cancelOrderUseCase: CancelOrderUseCase,
) :
    ViewModel() {

    val ordersItemUiState = MutableLiveData<OrdersItemUiState>()
    val ordersList = MutableLiveData<List<Order>>()
    var isLoading = false

    private var nexPageNumber = 0

    private val handlerException = CoroutineExceptionHandler { _, throwable ->
        ordersItemUiState.value = OrdersItemUiState.Error(throwable)
    }

    fun updateOrdersList(type: String) {
        isLoading = true
        ordersItemUiState.value = OrdersItemUiState.Loading
        viewModelScope.launch(handlerException) {
            val ordersResult = async {
                getOrdersUseCase.invoke(0)
            }

            ordersResult.await()
                .onSuccess { orders ->
                    withContext(Dispatchers.Main) {
                        val ordersActive = orders.filter { it.status == OrderStatus.in_work }
                        if (type == OrdersItemViewPager2Fragment.ALL_TYPE) {
                            ordersList.value = orders
                            if (orders.isNotEmpty()) {
                                nexPageNumber = 1
                                ordersItemUiState.value = OrdersItemUiState.ListDisplay
                            }
                        } else {
                            ordersList.value = ordersActive
                            if (ordersActive.isNotEmpty()) {
                                nexPageNumber = 1
                                ordersItemUiState.value = OrdersItemUiState.ListDisplay
                            }
                        }
                    }
                }
                .onFailure {
                    ordersItemUiState.value = OrdersItemUiState.Error(it)
                }

            isLoading = false
        }
    }

    fun loadOrderPage(type: String) {
        isLoading = true
        viewModelScope.launch(handlerException) {
            val ordersResult = async {
                getOrdersUseCase.invoke(nexPageNumber)
            }

            ordersResult.await()
                .onSuccess { newOrders ->
                    withContext(Dispatchers.Main) {
                        val newOrdersActive = newOrders.filter { it.status == OrderStatus.in_work }
                        if (type == OrdersItemViewPager2Fragment.ALL_TYPE) {
                            if (newOrders.isEmpty()) {
                                ordersItemUiState.value = OrdersItemUiState.FullListDisplay
                            } else {
                                nexPageNumber += 1
                                val oldOrders = ordersList.value
                                val allOrders = mutableListOf<Order>()
                                if (oldOrders != null) {
                                    allOrders.addAll(oldOrders)
                                }
                                allOrders.addAll(newOrders)
                                ordersList.value = allOrders
                                ordersItemUiState.value = OrdersItemUiState.ListDisplay
                            }
                        } else {
                            if (newOrdersActive.isEmpty()) {
                                ordersItemUiState.value = OrdersItemUiState.FullListDisplay
                            } else {
                                nexPageNumber += 1
                                val oldOrders = ordersList.value
                                val allOrders = mutableListOf<Order>()
                                if (oldOrders != null) {
                                    allOrders.addAll(oldOrders)
                                }
                                allOrders.addAll(newOrdersActive)
                                ordersList.value = allOrders
                                ordersItemUiState.value = OrdersItemUiState.ListDisplay
                            }
                        }
                    }
                }
                .onFailure {
                    ordersItemUiState.value = OrdersItemUiState.Error(it)
                }

            isLoading = false
        }
    }

    fun cancelOrder(id: String, type: String) {
        viewModelScope.launch(handlerException) {
            launch {
                cancelOrderUseCase.invoke(id)
            }.join()

            updateOrdersList(type)
        }
    }
}