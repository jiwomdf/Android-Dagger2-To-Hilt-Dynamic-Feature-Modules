package com.programmergabut.core.di

import android.content.Context
import android.content.SharedPreferences
import com.programmergabut.core.R
import com.programmergabut.core.domain.prefs.Prefs
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class SharedPrefModule {

    @Provides
    fun provideSharedPref(
        context: Context
    ): Prefs = Prefs(context)

}