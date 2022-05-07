package com.example.favmovie.di

import com.example.capstone.di.AppScope
import com.example.core.di.CoreComponent
import com.example.favmovie.feature.favmovie.FavMovieActivity
import dagger.Component

@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [FavMovieModule::class, FavMovieViewModelModule::class]
)
interface FavMovieComponent {
    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): FavMovieComponent
    }

    fun inject(activity: FavMovieActivity)
}