package com.example.capstone.di

import com.example.core.domain.repository.IMovieRepository
import com.example.core.domain.usecase.MovieUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@EntryPoint
@InstallIn(SingletonComponent::class)
interface SubModuleDependencies {
    fun provideIMovieRepository(): IMovieRepository
    fun proiveMovieUseCase(): MovieUseCase
}