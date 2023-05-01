package com.example.mainproject.di

import com.example.mainproject.domain.repositories.MockRepository
import com.example.mainproject.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetProductsUseCase(mockRepository: MockRepository): GetProductsUseCase {
        return GetProductsUseCase(mockRepository)
    }

    @Provides
    fun provideGetProfileUseCase(mockRepository: MockRepository): GetProfileUseCase {
        return GetProfileUseCase(mockRepository)
    }

    @Provides
    fun provideGetVersionApplicationUseCase(): GetVersionApplicationUseCase {
        return GetVersionApplicationUseCase()
    }

    @Provides
    fun provideSignInUseCase(mockRepository: MockRepository): SignInUseCase {
        return SignInUseCase(mockRepository)
    }

    @Provides
    fun provideSignOutUseCase(mockRepository: MockRepository): SignOutUseCase {
        return SignOutUseCase(mockRepository)
    }

    @Provides
    fun provideGetProductUseCase(mockRepository: MockRepository): GetProductUseCase {
        return GetProductUseCase(mockRepository)
    }

    @Provides
    fun provideGetOrdersUseCase(mockRepository: MockRepository): GetOrdersUseCase {
        return GetOrdersUseCase(mockRepository)
    }

    @Provides
    fun provideCancelOrderUseCase(mockRepository: MockRepository): CancelOrderUseCase {
        return CancelOrderUseCase(mockRepository)
    }
}