package com.example.mainproject.domain.usecases

import com.example.mainproject.data.CowboysRepository
import com.example.mainproject.data.CowboysSharedPreferences
import com.example.mainproject.domain.models.Profile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChangeProfileUseCase(
    private val cowboysRepository: CowboysRepository,
    private val cowboysSharedPreferences: CowboysSharedPreferences,
) {

    class AccessTokenInvalidException : Exception()
    class EmptyNameException : Exception()
    class EmptySurnameException : Exception()

    suspend operator fun invoke(newProfile: Profile): Result<Unit> {
        if (newProfile.name == "") {
            return Result.failure(EmptyNameException())
        }
        if (newProfile.surname == "") {
            return Result.failure(EmptySurnameException())
        }
        var resultUseCase: Result<Unit> = Result.failure(AccessTokenInvalidException())
        val token = cowboysSharedPreferences.getToken() ?: return resultUseCase
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            cowboysRepository.changeProfile(
                token,
                newProfile.name,
                newProfile.surname,
                newProfile.occupation,
                newProfile.avatarId
            )
        }
            .onSuccess {
                resultUseCase = Result.success(Unit)
            }
            .onFailure {
                resultUseCase = Result.failure(it)
            }
        return resultUseCase
    }
}