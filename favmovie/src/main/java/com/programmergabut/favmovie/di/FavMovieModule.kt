package com.programmergabut.favmovie.di

import com.programmergabut.core.domain.usecase.MovieInteractor
import com.programmergabut.core.domain.usecase.MovieUseCase
import dagger.Binds
import dagger.Module
import javax.inject.Named

@Module
abstract class FavMovieModule {
    @Binds
    @Named("FavMovieModule")
    abstract fun provideMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase
}