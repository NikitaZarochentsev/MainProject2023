package com.example.mainproject.presentation.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mainproject.domain.models.Profile
import com.example.mainproject.domain.usecases.GetProfileUseCase
import com.example.mainproject.domain.usecases.GetVersionApplicationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val getVersionApplicationUseCase: GetVersionApplicationUseCase
) : ViewModel() {

    val profile = MutableLiveData<Profile>()

    fun getProfile() {
        viewModelScope.launch {
            val profileResult = async {
                getProfileUseCase.invoke()
            }

            profileResult.await()
                .onSuccess {
                    profile.value = it
                }
        }
    }

    fun getVersionApplication(): String {
        return getVersionApplicationUseCase.invoke()
    }
}