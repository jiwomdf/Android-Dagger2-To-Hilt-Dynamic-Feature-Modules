package com.programmergabut.movieapp.di

import com.programmergabut.movieapp.feature.main.HomeFragment
import com.programmergabut.movieapp.feature.main.MainActivity
import com.programmergabut.core.di.CoreComponent
import dagger.Component

@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class, ViewModelModule::class]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: HomeFragment)
}