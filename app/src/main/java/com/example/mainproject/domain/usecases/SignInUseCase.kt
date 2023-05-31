package com.example.mainproject.domain.usecases

import com.example.mainproject.data.CowboysRepository
import com.example.mainproject.data.CowboysSharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class SignInUseCase(
    private val cowboysRepository: CowboysRepository,
    private val cowboysSharedPreferences: CowboysSharedPreferences,
) {

    class IllegalLoginException : Exception()
    class IllegalPasswordException : Exception()

    suspend operator fun invoke(login: String, password: String): Result<Any> {
        if (!loginVerify(login)) {
            return Result.failure(IllegalLoginException())
        }
        if (!passwordVerify(password)) {
            return Result.failure(IllegalPasswordException())
        }

        val result = CoroutineScope(Dispatchers.IO).async {
            cowboysRepository.signIn(login, password)
                .onSuccess { token ->
                    cowboysSharedPreferences.saveToken(token)
                    return@async Result.success(Unit)
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