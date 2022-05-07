package com.example.favmovie.di

import com.example.core.domain.usecase.MovieInteractor
import com.example.core.domain.usecase.MovieUseCase
import dagger.Binds
import dagger.Module
import javax.inject.Named
import javax.inject.Qualifier

@Module
abstract class FavMovieModule {
    @Binds
    @Named("FavMovieModule")
    abstract fun provideMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase
}