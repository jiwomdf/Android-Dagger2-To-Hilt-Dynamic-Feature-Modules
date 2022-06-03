package com.programmergabut.moviedetail.di

import androidx.lifecycle.ViewModel
import com.programmergabut.movieapp.di.ViewModelKey
import com.programmergabut.moviedetail.feature.detail.DetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Suppress("unused")
@Module
abstract class MovieDetailViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindDetailMovieViewModel(viewModel: DetailViewModel): ViewModel
}