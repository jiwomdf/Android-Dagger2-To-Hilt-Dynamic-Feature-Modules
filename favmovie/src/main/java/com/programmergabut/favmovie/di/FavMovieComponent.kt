package com.programmergabut.favmovie.di

import com.programmergabut.movieapp.di.AppScope
import com.programmergabut.core.di.CoreComponent
import com.programmergabut.favmovie.feature.favmovie.FavMovieActivity
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