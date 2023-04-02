package com.example.mainproject.domain.usecases

import com.example.mainproject.domain.repositories.MockRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlin.math.log

class SignInUseCase(private val mockRepository: MockRepository) {

    class IllegalLoginException : Exception()
    class IllegalPasswordException : Exception()

    suspend operator fun invoke(login: String, password: String): Result<String> {
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
        val emailRegex = "[A-Za-z0-9]+@[A-Za-z0-9]+.[A-Za-z0-9]+"
        return login.contains(emailRegex.toRegex())
    }

    private fun passwordVerify(password: String): Boolean {
        return password.length > 7
    }
}