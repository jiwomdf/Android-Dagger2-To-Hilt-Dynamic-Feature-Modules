package com.example.moviedetail.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviedetail.feature.detail.DetailViewModel
import com.example.moviedetail.ui.MovieDetailViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Suppress("unused")
@Module
abstract class MovieDetailViewModelModule {

    @Binds
    @IntoMap
    @MovieDetailViewModelKey(DetailViewModel::class)
    abstract fun bindDetailMovieViewModel(viewModel: DetailViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: MovieDetailViewModelFactory): ViewModelProvider.Factory
}