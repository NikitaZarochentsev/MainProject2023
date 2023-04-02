package com.example.mainproject.di

import com.example.mainproject.data.MockRepositoryImpl
import com.example.mainproject.domain.repositories.MockRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideMockRepository(): MockRepository {
        return MockRepositoryImpl()
    }
}