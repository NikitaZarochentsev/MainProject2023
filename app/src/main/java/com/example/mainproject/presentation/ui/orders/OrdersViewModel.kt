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
class OrdersViewModel @Inject constructor(
    private val getOrdersUseCase: GetOrdersUseCase,
    private val cancelOrderUseCase: CancelOrderUseCase,
) : ViewModel() {

    val ordersUiState = MutableLiveData<OrdersUiState>()
    val ordersAll = MutableLiveData<MutableList<Order>>()
    val ordersActive = MutableLiveData<MutableList<Order>>()
    var isLoading = false

    private var nextPageNumber = 1

    private val handlerException = CoroutineExceptionHandler { _, throwable ->
        ordersUiState.value = OrdersUiState.Error(throwable)
    }

    fun updateOrderList() {
        isLoading = true
        ordersUiState.value = OrdersUiState.Loading
        viewModelScope.launch(handlerException) {
            val ordersResult = async {
                getOrdersUseCase.invoke(1)
            }

            ordersResult.await()
                .onSuccess { newOrders ->
                    withContext(Dispatchers.Main) {
                        ordersAll.value = newOrders.toMutableList()
                        val newOrdersActive =
                            newOrders.filter { it.status == OrderStatus.in_work }.toMutableList()
                        ordersActive.value = newOrdersActive
                        nextPageNumber = 2
                        ordersUiState.value = OrdersUiState.ListDisplay(newOrdersActive.isEmpty())
                    }
                }
                .onFailure {
                    ordersUiState.value = OrdersUiState.Error(it)
                }

            isLoading = false
        }
    }

    fun loadOrderPage() {
        isLoading = true
        viewModelScope.launch(handlerException) {
            val ordersResult = async {
                getOrdersUseCase.invoke(nextPageNumber)
            }

            ordersResult.await()
                .onSuccess { newPageOrders ->
                    withContext(Dispatchers.Main) {
                        if (newPageOrders.isEmpty()) {
                            val currentOrdersActive = ordersActive.value
                            ordersUiState.value =
                                OrdersUiState.FullListDisplay(currentOrdersActive == null || currentOrdersActive.isEmpty())
                        } else {
                            nextPageNumber += 1
                            val oldOrdersAll = ordersAll.value
                            val unionOrdersAll = mutableListOf<Order>()
                            if (oldOrdersAll != null) {
                                unionOrdersAll.addAll(oldOrdersAll)
                            }
                            unionOrdersAll.addAll(newPageOrders)
                            ordersAll.value = unionOrdersAll
                            val newPageOrdersActive =
                                newPageOrders.filter { it.status == OrderStatus.in_work }
                            val unionOrdersActive =
                                unionOrdersAll.filter { it.status == OrderStatus.in_work }
                            if (newPageOrdersActive.isNotEmpty()) {
                                ordersActive.value = unionOrdersActive.toMutableList()
                            }
                            ordersUiState.value =
                                OrdersUiState.ListDisplay(unionOrdersActive.isEmpty())
                        }
                    }
                }
                .onFailure {
                    ordersUiState.value = OrdersUiState.Error(it)
                }

            isLoading = false
        }
    }

    fun cancelOrder(id: String) {
        viewModelScope.launch(handlerException) {
            withContext(Dispatchers.Default) {
                cancelOrderUseCase.invoke(id)
            }
                .onSuccess {
                    ordersAll.value?.find { it.id == id }?.status = OrderStatus.cancelled
                    ordersActive.value?.removeAll { it.id == id }

                    ordersAll.value = ordersAll.value
                    ordersActive.value = ordersActive.value

                    val currentOrdersActive = ordersActive.value
                    if (ordersUiState.value is OrdersUiState.ListDisplay) {
                        ordersUiState.value =
                            OrdersUiState.ListDisplay(currentOrdersActive == null || currentOrdersActive.isEmpty())
                    }
                    if (ordersUiState.value is OrdersUiState.FullListDisplay) {
                        ordersUiState.value =
                            OrdersUiState.FullListDisplay(currentOrdersActive == null || currentOrdersActive.isEmpty())
                    }
                }
                .onFailure {
                    // ошибка отмены
                }
        }
    }
}