package com.example.mainproject.domain.usecases

import com.example.mainproject.domain.models.Profile
import com.example.mainproject.domain.repositories.MockRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class SignInUseCase(private val mockRepository: MockRepository) {

    class IllegalLoginException : Exception()
    class IllegalPasswordException : Exception()

    suspend operator fun invoke(login: String, password: String): Result<Profile> {
        if (!loginVerify(login)) {
            return Result.failure(IllegalLoginException())
        }
        if (!passwordVerify(password)) {
            return Result.failure(IllegalPasswordException())
        }

        val result = CoroutineScope(Dispatchers.IO).async {
            val signInResult = async {
                mockRepository.signIn(login, password)
            }

            signInResult.await()
                .onSuccess {
                    return@async Result.success(it)
                }
                .onFailure {
                    Result.failure<Throwable>(it)
                }
        }

        return result.await()
    }

    private fun loginVerify(login: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(login).matches()
    }

    private fun passwordVerify(password: String): Boolean {
        return password.length > 7
    }
}