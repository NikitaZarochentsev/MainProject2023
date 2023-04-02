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

    val signInUiState = MutableLiveData<SignInState>()

    val token = MutableLiveData<String>()

    fun signIn(login: String, password: String) {
        signInUiState.value = SignInState.Loading

        viewModelScope.launch {
            val signInResult = async {
                signInUseCase.invoke(login, password)
            }

            signInResult.await()
                .onSuccess {
                    withContext(Dispatchers.Main) {
                        token.value = it
                    }
                }
                .onFailure {
                    when (it) {
                        is SignInUseCase.IllegalLoginException -> signInUiState.value =
                            SignInState.LoginError
                        is SignInUseCase.IllegalPasswordException -> signInUiState.value =
                            SignInState.PasswordError
                        else -> signInUiState.value = SignInState.Error
                    }
                }
        }
    }
}

sealed class SignInState {
    object Loading : SignInState()
    object LoginError : SignInState()
    object PasswordError : SignInState()
    object Error : SignInState()
}