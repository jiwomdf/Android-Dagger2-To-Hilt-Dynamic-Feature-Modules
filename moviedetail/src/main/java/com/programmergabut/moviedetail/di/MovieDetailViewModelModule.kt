package com.programmergabut.moviedetail.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.programmergabut.core.factory.ViewModelFactory
import com.programmergabut.moviedetail.feature.detail.DetailViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.multibindings.IntoMap


@Module
@InstallIn(ViewModelComponent::class)
abstract class MovieDetailViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @MovieDetailViewModelKey(DetailViewModel::class)
    abstract fun bindDetailMovieViewModel(viewModel: DetailViewModel): ViewModel
}