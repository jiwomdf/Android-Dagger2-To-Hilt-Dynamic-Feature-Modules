package com.programmergabut.favmovie.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.programmergabut.core.factory.ViewModelFactory
import com.programmergabut.favmovie.feature.favmovie.FavMovieViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.multibindings.IntoMap


@Module
@InstallIn(ViewModelComponent::class)
abstract class FavMovieViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @FavMovieViewModelKey(FavMovieViewModel::class)
    abstract fun bindDetailMovieViewModel(viewModel: FavMovieViewModel): ViewModel
}