package com.example.capstone.di

import com.example.core.domain.usecase.MovieInteractor
import com.example.core.domain.usecase.MovieUseCase
import dagger.Binds
import dagger.Module
import javax.inject.Named

@Module
abstract class AppModule {
    @Binds
    @Named("AppModule")
    abstract fun provideMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase
}