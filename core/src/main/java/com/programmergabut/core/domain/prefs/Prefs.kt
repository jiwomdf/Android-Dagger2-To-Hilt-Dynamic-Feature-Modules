package com.programmergabut.core.domain.prefs

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context)
{
    companion object {
        private const val PREFS_NAME = "movie_app_prefs"
        private const val APP_THEME_MODE = "intExamplePref"
    }

    private val preferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var isDarkThemeMode: Boolean
        get() = preferences.getBoolean(APP_THEME_MODE, true)
        set(value) = preferences.edit().putBoolean(APP_THEME_MODE, value).apply()
}