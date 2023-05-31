package com.example.mainproject.presentation.ui.settings

sealed class SettingsUiState {
    object Success : SettingsUiState()
    object Error : SettingsUiState()
    object EmptyName : SettingsUiState()
    object EmptySurname : SettingsUiState()
    object Loading : SettingsUiState()
}
