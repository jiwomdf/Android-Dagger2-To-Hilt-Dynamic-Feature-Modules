package com.programmergabut.movieapp

import android.app.Application
import com.programmergabut.movieapp.di.AppComponent
import com.programmergabut.movieapp.di.DaggerAppComponent
import com.programmergabut.core.di.CoreComponent
import com.programmergabut.core.di.DaggerCoreComponent

open class App: Application() {

    val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().create(applicationContext)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(coreComponent)
    }
}