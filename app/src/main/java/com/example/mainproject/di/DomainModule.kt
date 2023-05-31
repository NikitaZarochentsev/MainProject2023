package com.example.mainproject.di

import com.example.mainproject.data.CowboysRepository
import com.example.mainproject.data.CowboysSharedPreferences
import com.example.mainproject.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetProductsUseCase(
        cowboysRepository: CowboysRepository,
        cowboysSharedPreferences: CowboysSharedPreferences,
    ): GetProductsUseCase {
        return GetProductsUseCase(cowboysRepository, cowboysSharedPreferences)
    }

    @Provides
    fun provideGetProfileUseCase(
        cowboysRepository: CowboysRepository,
        cowboysSharedPreferences: CowboysSharedPreferences,
    ): GetProfileUseCase {
        return GetProfileUseCase(cowboysRepository, cowboysSharedPreferences)
    }

    @Provides
    fun provideGetAvatarUseCase(
        cowboysRepository: CowboysRepository,
        cowboysSharedPreferences: CowboysSharedPreferences,
    ): GetAvatarUseCase {
        return GetAvatarUseCase(cowboysRepository, cowboysSharedPreferences)
    }

    @Provides
    fun provideGetVersionApplicationUseCase(): GetVersionApplicationUseCase {
        return GetVersionApplicationUseCase()
    }

    @Provides
    fun provideSignInUseCase(
        cowboysRepository: CowboysRepository,
        cowboysSharedPreferences: CowboysSharedPreferences,
    ): SignInUseCase {
        return SignInUseCase(cowboysRepository, cowboysSharedPreferences)
    }

    @Provides
    fun provideSignOutUseCase(cowboysSharedPreferences: CowboysSharedPreferences): SignOutUseCase {
        return SignOutUseCase(cowboysSharedPreferences)
    }

    @Provides
    fun provideGetProductUseCase(
        cowboysRepository: CowboysRepository,
        cowboysSharedPreferences: CowboysSharedPreferences,
    ): GetProductUseCase {
        return GetProductUseCase(cowboysRepository, cowboysSharedPreferences)
    }

    @Provides
    fun provideGetOrdersUseCase(
        cowboysRepository: CowboysRepository,
        cowboysSharedPreferences: CowboysSharedPreferences,
    ): GetOrdersUseCase {
        return GetOrdersUseCase(cowboysRepository, cowboysSharedPreferences)
    }

    @Provides
    fun provideCancelOrderUseCase(
        cowboysRepository: CowboysRepository,
        cowboysSharedPreferences: CowboysSharedPreferences,
    ): CancelOrderUseCase {
        return CancelOrderUseCase(cowboysRepository, cowboysSharedPreferences)
    }

    @Provides
    fun provideCreateOrderUseCase(
        cowboysRepository: CowboysRepository,
        cowboysSharedPreferences: CowboysSharedPreferences,
    ): CreateOrderUseCase {
        return CreateOrderUseCase(cowboysRepository, cowboysSharedPreferences)
    }

    @Provides
    fun provideChangeProfileUseCase(
        cowboysRepository: CowboysRepository,
        cowboysSharedPreferences: CowboysSharedPreferences,
    ): ChangeProfileUseCase {
        return ChangeProfileUseCase(cowboysRepository, cowboysSharedPreferences)
    }

    @Provides
    fun provideChangePhotoUseCase(
        cowboysRepository: CowboysRepository,
        cowboysSharedPreferences: CowboysSharedPreferences,
    ): ChangePhotoUseCase {
        return ChangePhotoUseCase(cowboysRepository, cowboysSharedPreferences)
    }
}