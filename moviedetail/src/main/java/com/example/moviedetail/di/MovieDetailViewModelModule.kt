package com.example.moviedetail.di

import androidx.lifecycle.ViewModel
import com.example.capstone.di.ViewModelKey
import com.example.moviedetail.feature.detail.DetailViewModel
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