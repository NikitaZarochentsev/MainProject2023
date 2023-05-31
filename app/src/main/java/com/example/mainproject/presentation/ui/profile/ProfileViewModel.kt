package com.example.mainproject.presentation.ui.profile

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mainproject.domain.usecases.GetAvatarUseCase
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
    private val getAvatarUseCase: GetAvatarUseCase,
    private val getVersionApplicationUseCase: GetVersionApplicationUseCase,
    private val signOutUseCase: SignOutUseCase,
) : ViewModel() {

    val profileUiState = MutableLiveData<ProfileUiState>()
    val avatar = MutableLiveData<Bitmap>()
    val versionApp = MutableLiveData<Pair<String, String>>()

    fun getProfile() {
        viewModelScope.launch {
            val profileResult = async {
                getProfileUseCase.invoke()
            }

            profileResult.await()
                .onSuccess { profile ->
                    profileUiState.value = ProfileUiState.Default(profile)
                    val avatarResult = async {
                        getAvatarUseCase.invoke(profile.avatarId)
                    }

                    avatarResult.await()
                        .onSuccess { avatarUrl ->
                            avatar.value = avatarUrl
                        }
                }
        }
    }

    fun getVersionApplication() {
        versionApp.value = getVersionApplicationUseCase.invoke()
    }

    fun signOut() {
        signOutUseCase.invoke()
        profileUiState.value = ProfileUiState.Out
    }
}