package com.example.mainproject.domain.usecases

import com.example.mainproject.domain.models.Profile
import com.example.mainproject.domain.repositories.MockRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class GetProfileUseCase(private val mockRepository: MockRepository) {

    suspend operator fun invoke(): Result<Profile> {
        val products = CoroutineScope(Dispatchers.IO).async {
            val profileResult = async {
                mockRepository.getProfile()
            }

            profileResult.await()
                .onSuccess {
                    return@async Result.success(it)
                }
                .onFailure {
                    Result.failure<Throwable>(it)
                }
        }

        return products.await()
    }
}