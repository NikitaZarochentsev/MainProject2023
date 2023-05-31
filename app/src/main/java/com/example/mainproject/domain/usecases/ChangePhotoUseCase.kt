package com.example.mainproject.domain.usecases

import android.graphics.Bitmap
import com.example.mainproject.data.CowboysRepository
import com.example.mainproject.data.CowboysSharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class ChangePhotoUseCase(
    private val cowboysRepository: CowboysRepository,
    private val cowboysSharedPreferences: CowboysSharedPreferences,
) {

    class AccessTokenInvalidException : Exception()

    suspend operator fun invoke(image: Bitmap): Result<Unit> {
        var resultUseCase: Result<Unit> = Result.failure(AccessTokenInvalidException())
        val token = cowboysSharedPreferences.getToken() ?: return resultUseCase

        val stream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 80, stream)
        val byteArray = stream.toByteArray()

        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            cowboysRepository.changePhoto(token, byteArray)
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