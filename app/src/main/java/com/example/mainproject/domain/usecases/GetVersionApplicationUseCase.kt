package com.example.mainproject.domain.usecases

import com.example.mainproject.BuildConfig

class GetVersionApplicationUseCase {

    operator fun invoke(): Pair<String, String> {

        val versionName = BuildConfig.VERSION_NAME
        val versionCode = BuildConfig.VERSION_CODE

        return Pair(versionName, versionCode.toString())
    }
}