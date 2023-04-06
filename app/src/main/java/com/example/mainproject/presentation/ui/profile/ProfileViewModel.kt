package com.example.mainproject.presentation.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mainproject.domain.usecases.GetProfileUseCase
import com.example.mainproject.domain.usecases.GetVersionApplicationUseCase
import com.example.mainproject.domain.usecases.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val getVersionApplicationUseCase: GetVersionApplicationUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    val profileUiState = MutableLiveData<ProfileUiState>()

    fun getProfile() {
        viewModelScope.launch {
            val profileResult = async {
                getProfileUseCase.invoke()
            }

            profileResult.await()
                .onSuccess {
                    profileUiState.value = ProfileUiState.Default(it)
                }
        }
    }

    fun getVersionApplication(): Pair<String, String> {
        return getVersionApplicationUseCase.invoke()
    }

    fun signOut() {
        signOutUseCase.invoke()
        profileUiState.value = ProfileUiState.Out
    }
}