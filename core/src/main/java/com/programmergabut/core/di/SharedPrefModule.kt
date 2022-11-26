package com.programmergabut.core.di

import android.content.Context
import android.content.SharedPreferences
import com.programmergabut.core.R
import com.programmergabut.core.domain.prefs.Prefs
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
    ): Prefs = Prefs(context)
}