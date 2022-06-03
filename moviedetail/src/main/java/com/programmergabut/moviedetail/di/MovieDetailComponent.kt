package com.programmergabut.moviedetail.di

import android.content.Context
import com.programmergabut.movieapp.di.SubModuleDependencies
import com.programmergabut.moviedetail.feature.detail.DetailMovieActivity
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