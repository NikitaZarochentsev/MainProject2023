package com.example.mainproject.domain.repositories

import com.example.mainproject.domain.models.Product
import com.example.mainproject.domain.models.Profile

interface MockRepository {
    suspend fun signIn(login: String, password: String): Result<Boolean>

    suspend fun getProducts(): Result<List<Product>>

    suspend fun getProfile(): Result<Profile>

    fun signOut()
}