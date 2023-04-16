package com.example.mainproject.presentation.ui.orders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mainproject.domain.models.OrderStatus
import com.example.mainproject.domain.usecases.GetOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class OrdersItemViewModel @Inject constructor(private val getOrdersUseCase: GetOrdersUseCase) :
    ViewModel() {

    val ordersItemUiState = MutableLiveData<OrdersItemUiState>()

    private val handlerException = CoroutineExceptionHandler { _, throwable ->
        ordersItemUiState.value = OrdersItemUiState.Error(throwable)
    }

    fun updateOrdersList(type: String) {
        ordersItemUiState.value = OrdersItemUiState.Loading
        viewModelScope.launch(handlerException) {
            val ordersResult = async {
                getOrdersUseCase.invoke()
            }

            ordersResult.await()
                .onSuccess { orders ->
                    withContext(Dispatchers.Main) {
                        if (orders.isEmpty()) {
                            ordersItemUiState.value = OrdersItemUiState.Empty
                        } else {
                            val ordersActive = orders.filter { it.status == OrderStatus.in_work }
                            countItemsViewPager.value = orders.size to ordersActive.size
                            if (type == OrdersItemViewPager2Fragment.ALL_TYPE) {
                                ordersItemUiState.value = OrdersItemUiState.Default(orders)
                            } else {
                                ordersItemUiState.value =
                                    OrdersItemUiState.Default(ordersActive)
                            }
                        }
                    }
                }
                .onFailure {
                    ordersItemUiState.value = OrdersItemUiState.Error(it)
                }
        }
    }
}