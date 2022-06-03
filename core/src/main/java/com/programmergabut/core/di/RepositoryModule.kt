package com.programmergabut.core.di

import com.programmergabut.core.data.repository.MovieRepository
import com.programmergabut.core.domain.repository.IMovieRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class, DatabaseModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun provideMovieRepository(repository: MovieRepository): IMovieRepository

}