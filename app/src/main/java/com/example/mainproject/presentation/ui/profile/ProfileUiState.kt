package com.example.mainproject.presentation.ui.profile

import com.example.mainproject.domain.models.Profile

sealed class ProfileUiState {
    data class Default(var profile: Profile) : ProfileUiState()
    object Out : ProfileUiState()
}
