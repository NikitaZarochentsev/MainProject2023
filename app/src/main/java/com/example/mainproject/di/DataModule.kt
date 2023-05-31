package com.example.mainproject.di

import android.content.Context
import com.example.mainproject.data.CowboysRepository
import com.example.mainproject.data.CowboysSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideCowboysRepository(): CowboysRepository {
        return CowboysRepository()
    }

    @Provides
    @Singleton
    fun provideCowboysSharedPreferences(@ApplicationContext context: Context): CowboysSharedPreferences {
        return CowboysSharedPreferences(context)
    }
}