package com.example.mainproject.presentation

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainProjectApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("dce9a62b-94ea-4f75-ab97-49757f1fd465")
    }
}