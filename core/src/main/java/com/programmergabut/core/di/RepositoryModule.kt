package com.programmergabut.core.di

import com.programmergabut.core.data.repository.MovieRepository
import com.programmergabut.core.domain.repository.IMovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideMovieRepository(repository: MovieRepository): IMovieRepository

}