package com.programmergabut.moviedetail.di

import com.programmergabut.movieapp.di.AppScope
import com.programmergabut.core.di.CoreComponent
import com.programmergabut.moviedetail.feature.detail.DetailMovieActivity
import dagger.Component

@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [MovieDetailModule::class, MovieDetailViewModelModule::class]
)
interface MovieDetailComponent {
    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): MovieDetailComponent
    }

    fun inject(activity: DetailMovieActivity)
}