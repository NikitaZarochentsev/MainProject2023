package com.example.mainproject.presentation.ui.neworder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mainproject.domain.usecases.CreateOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewOrderViewModel @Inject constructor(private val createOrderUseCase: CreateOrderUseCase) :
    ViewModel() {

    val newOrderUiState = MutableLiveData<NewOrderUiState>()
    private var etd = ""

    fun setEtd(etd: String) {
        this.etd = etd
    }

    fun createOrder(
        productId: String,
        quantity: Int,
        size: String,
        house: String,
        apartment: String,
    ) {
        if (etd != "") {
            viewModelScope.launch {
                createOrderUseCase.invoke(
                    productId,
                    quantity,
                    size,
                    house,
                    apartment,
                    etd
                )
                    .onSuccess {
                        newOrderUiState.value = NewOrderUiState.Success(1)
                    }
                    .onFailure {
                        newOrderUiState.value = NewOrderUiState.Error
                    }
            }
        }
    }

    sealed class NewOrderUiState {
        data class Success(val numberOrder: Int) : NewOrderUiState()
        object Error : NewOrderUiState()
    }
}