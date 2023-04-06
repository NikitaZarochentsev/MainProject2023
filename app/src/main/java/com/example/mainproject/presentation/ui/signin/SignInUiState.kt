package com.example.mainproject.presentation.ui.signin

sealed class SignInUiState {
    object Loading : SignInUiState()
    object LoginError : SignInUiState()
    object PasswordError : SignInUiState()
    object Error : SignInUiState()
    object Success: SignInUiState()
}