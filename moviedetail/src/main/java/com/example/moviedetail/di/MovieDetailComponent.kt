package com.example.moviedetail.di

import android.content.Context
import com.example.capstone.di.SubModuleDependencies
import com.example.moviedetail.feature.detail.DetailMovieActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [SubModuleDependencies::class],
    modules = [MovieDetailViewModelModule::class]
)
@Singleton
interface MovieDetailComponent {

    fun inject(activity: DetailMovieActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun dependencies(component: SubModuleDependencies): Builder
        fun build(): MovieDetailComponent
    }

}