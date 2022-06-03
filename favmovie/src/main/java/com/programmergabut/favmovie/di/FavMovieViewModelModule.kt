package com.programmergabut.favmovie.di

import androidx.lifecycle.ViewModel
import com.programmergabut.movieapp.di.ViewModelKey
import com.programmergabut.favmovie.feature.favmovie.FavMovieViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Suppress("unused")
@Module
abstract class FavMovieViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FavMovieViewModel::class)
    abstract fun bindDetailMovieViewModel(viewModel: FavMovieViewModel): ViewModel
}