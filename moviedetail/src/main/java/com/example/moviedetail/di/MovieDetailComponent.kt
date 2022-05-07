package com.example.moviedetail.di

import com.example.capstone.di.AppScope
import com.example.core.di.CoreComponent
import com.example.moviedetail.feature.detail.DetailMovieActivity
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