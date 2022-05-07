package com.example.core.di

import android.content.Context
import android.content.SharedPreferences
import com.example.core.R
import dagger.Module
import javax.inject.Inject

@Module
class SharedPrefModule {

    @Inject
    fun provideSharedPref(
        context: Context
    ): SharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_pref_tmdb), Context.MODE_PRIVATE)
}