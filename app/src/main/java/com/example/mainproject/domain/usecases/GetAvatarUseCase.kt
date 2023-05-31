package com.example.mainproject.domain.usecases

import android.graphics.Bitmap
import com.example.mainproject.data.CowboysRepository
import com.example.mainproject.data.CowboysSharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAvatarUseCase(
    private val cowboysRepository: CowboysRepository,
    private val cowboysSharedPreferences: CowboysSharedPreferences
) {

    class AccessTokenInvalidException : Exception()

    suspend operator fun invoke(id: String): Result<Bitmap> {
        var resultUseCase: Result<Bitmap> = Result.failure(AccessTokenInvalidException())
        val token = cowboysSharedPreferences.getToken() ?: return resultUseCase
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            cowboysRepository.getAvatar(token, id)
        }
            .onSuccess {
                resultUseCase = Result.success(it)
            }
            .onFailure {
                Result.failure<Throwable>(it)
            }
        return resultUseCase
    }
}