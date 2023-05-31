package com.example.mainproject.domain.usecases

import com.example.mainproject.data.CowboysRepository
import com.example.mainproject.data.CowboysSharedPreferences
import com.example.mainproject.domain.models.Profile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetProfileUseCase(
    private val cowboysRepository: CowboysRepository,
    private val cowboysSharedPreferences: CowboysSharedPreferences,
) {

    class AccessTokenInvalidException : Exception()

    suspend operator fun invoke(): Result<Profile> {
        var resultUseCase: Result<Profile> = Result.failure(AccessTokenInvalidException())
        val token = cowboysSharedPreferences.getToken() ?: return resultUseCase
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            cowboysRepository.getProfile(token)
        }
            .onSuccess {
                resultUseCase = Result.success(it.toProfileDomain())
            }
            .onFailure {
                Result.failure<Throwable>(it)
            }
        return resultUseCase
    }
}