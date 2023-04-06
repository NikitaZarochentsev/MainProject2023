package com.example.mainproject.domain.usecases

import com.example.mainproject.domain.repositories.MockRepository

class SignOutUseCase(private val mockRepository: MockRepository) {

    operator fun invoke() {
        mockRepository.signOut()
    }
}