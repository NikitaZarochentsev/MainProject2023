package com.example.mainproject.presentation.ui.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mainproject.domain.usecases.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val signInUseCase: SignInUseCase) : ViewModel() {

    val signInUiState = MutableLiveData<SignInUiState>()

    fun signIn(login: String, password: String) {
        signInUiState.value = SignInUiState.Loading

        viewModelScope.launch {
            val signInResult = async {
                signInUseCase.invoke(login, password)
            }

            signInResult.await()
                .onSuccess {
                    withContext(Dispatchers.Main) {
                        signInUiState.value = SignInUiState.Success
                    }
                }
                .onFailure {
                    when (it) {
                        is SignInUseCase.IllegalLoginException -> signInUiState.value =
                            SignInUiState.LoginError
                        is SignInUseCase.IllegalPasswordException -> signInUiState.value =
                            SignInUiState.PasswordError
                        else -> signInUiState.value = SignInUiState.Error
                    }
                }
        }
    }
}