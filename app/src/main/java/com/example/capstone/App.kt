package com.example.capstone

import android.app.Application
import com.example.capstone.di.AppComponent
import com.example.capstone.di.DaggerAppComponent
import com.example.core.di.CoreComponent
import com.example.core.di.DaggerCoreComponent

open class App: Application() {

    val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().create(applicationContext)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(coreComponent)
    }
}