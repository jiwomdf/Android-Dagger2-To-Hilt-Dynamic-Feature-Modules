package com.programmergabut.movieapp.di

import com.programmergabut.core.domain.prefs.Prefs
import com.programmergabut.core.domain.repository.IMovieRepository
import com.programmergabut.core.domain.usecase.MovieUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface SubModuleDependencies {
    fun proiveMovieUseCase(): MovieUseCase
    fun proivePrefs(): Prefs
}