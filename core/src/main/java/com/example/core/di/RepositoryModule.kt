package com.example.core.di

import com.example.core.data.repository.MovieRepository
import com.example.core.domain.repository.IMovieRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class, DatabaseModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun provideMovieRepository(repository: MovieRepository): IMovieRepository

}