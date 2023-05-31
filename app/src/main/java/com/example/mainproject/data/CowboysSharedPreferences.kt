package com.example.mainproject.data

import android.content.Context

class CowboysSharedPreferences(val context: Context) {

    private val preferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    companion object {
        private const val APP_PREFERENCES = "APP_PREFERENCES"
        private const val APP_PREFERENCES_TOKEN = "APP_PREFERENCES_TOKEN"
    }

    fun saveToken(token: String) {
        val editor = preferences.edit()
        editor.putString(APP_PREFERENCES_TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? {
        return if (preferences.contains(APP_PREFERENCES_TOKEN)) {
            preferences.getString(APP_PREFERENCES_TOKEN, "")
        } else {
            null
        }
    }

    fun removeToken() {
        val editor = preferences.edit()
        editor.remove(APP_PREFERENCES_TOKEN)
        editor.apply()
    }
}