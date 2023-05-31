package com.example.mainproject.domain.usecases

import com.example.mainproject.data.CowboysSharedPreferences

class SignOutUseCase(
    private val cowboysSharedPreferences: CowboysSharedPreferences,
) {

    operator fun invoke() {
        cowboysSharedPreferences.removeToken()
    }
}