package com.programmergabut.moviedetail.di

import com.programmergabut.core.domain.usecase.MovieInteractor
import com.programmergabut.core.domain.usecase.MovieUseCase
import dagger.Binds
import dagger.Module
import javax.inject.Named

@Module
abstract class MovieDetailModule {
    @Binds
    @Named("MovieDetailModule")
    abstract fun provideMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase
}