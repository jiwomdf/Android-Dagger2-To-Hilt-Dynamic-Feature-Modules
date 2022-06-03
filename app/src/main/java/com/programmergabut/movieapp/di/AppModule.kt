package com.programmergabut.movieapp.di

import com.programmergabut.core.domain.usecase.MovieInteractor
import com.programmergabut.core.domain.usecase.MovieUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun provideMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase
}