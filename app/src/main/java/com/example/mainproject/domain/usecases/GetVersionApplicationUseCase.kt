package com.example.mainproject.domain.usecases

import com.example.mainproject.BuildConfig

class GetVersionApplicationUseCase {

    operator fun invoke(): String {

        val versionCode = BuildConfig.VERSION_CODE
        val versionName = BuildConfig.VERSION_NAME

        return "Приложение $versionName ($versionCode)"
    }
}