package com.programmergabut.movieapp.di

import com.programmergabut.core.domain.usecase.MovieInteractor
import com.programmergabut.core.domain.usecase.MovieUseCase
import dagger.Binds
import dagger.Module
import javax.inject.Named

@Module
abstract class AppModule {
    @Binds
    @Named("AppModule")
    abstract fun provideMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase
}