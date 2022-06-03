package com.programmergabut.core.di

import android.content.Context
import android.content.SharedPreferences
import com.programmergabut.core.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class SharedPrefModule {

    @Singleton
    @Provides
    fun provideSharedPref(
        @ApplicationContext context: Context
    ): SharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_pref_tmdb), Context.MODE_PRIVATE)
}