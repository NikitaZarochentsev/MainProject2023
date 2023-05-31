package com.example.mainproject.presentation.ui.settings

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mainproject.domain.models.Profile
import com.example.mainproject.domain.usecases.ChangePhotoUseCase
import com.example.mainproject.domain.usecases.ChangeProfileUseCase
import com.example.mainproject.domain.usecases.GetAvatarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val changeProfileUseCase: ChangeProfileUseCase,
    private val changePhotoUseCase: ChangePhotoUseCase,
    private val getAvatarUseCase: GetAvatarUseCase,
) :
    ViewModel() {

    val settingsUiState = MutableLiveData<SettingsUiState>()
    val avatar = MutableLiveData<Bitmap>()

    fun setImageUri(newImage: Bitmap) {
        avatar.value = newImage
    }

    fun getAvatar(id: String) {
        viewModelScope.launch {
            val imageResult = async {
                getAvatarUseCase.invoke(id)
            }
            imageResult.await()
                .onSuccess { image ->
                    avatar.value = image
                }
        }
    }

    fun changeProfile(newProfile: Profile) {
        settingsUiState.value = SettingsUiState.Loading
        viewModelScope.launch {
            if (avatar.value != null) {
                val newImage = avatar.value!!
                changePhotoUseCase.invoke(newImage)
                    .onSuccess {
                        settingsUiState.value = SettingsUiState.Success
                    }
                    .onFailure {
                        settingsUiState.value = SettingsUiState.Error
                    }
            }

            changeProfileUseCase.invoke(newProfile)
                .onSuccess {
                    settingsUiState.value = SettingsUiState.Success
                }
                .onFailure {
                    when (it) {
                        is ChangeProfileUseCase.EmptyNameException -> {
                            settingsUiState.value = SettingsUiState.EmptyName
                        }
                        is ChangeProfileUseCase.EmptySurnameException -> {
                            settingsUiState.value = SettingsUiState.EmptySurname
                        }
                        else -> {
                            settingsUiState.value = SettingsUiState.Error
                        }
                    }
                }

        }
    }
}