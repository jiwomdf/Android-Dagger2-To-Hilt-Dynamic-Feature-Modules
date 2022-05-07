package com.example.favmovie.di

import androidx.lifecycle.ViewModel
import com.example.favmovie.feature.favmovie.FavMovieViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Suppress("unused")
@Module
abstract class FavMovieViewModelModule {

    @Binds
    @IntoMap
    @FavMovieViewModelKey(FavMovieViewModel::class)
    abstract fun bindDetailMovieViewModel(viewModel: FavMovieViewModel): ViewModel
}